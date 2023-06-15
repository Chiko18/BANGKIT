const { admin, db } = require("../utils/admin.js");
const firebaseConfig = require("../../firebaseConfig.js");
const { validateSignup, validateLogin } = require("../utils/validator.js");

const firebase = require("firebase/app");
const {
	getAuth,
	signInWithEmailAndPassword,
	createUserWithEmailAndPassword,
	sendPasswordResetEmail,
	sendEmailVerification,
	updateProfile,
} = require("firebase/auth");

const { v4: uuidv4 } = require("uuid");

firebase.initializeApp(firebaseConfig);

exports.signupController = async (req, res) => {
	const { name, email, password, confirmPassword, address } = req.body;
	const newUser = {
		name,
		email,
		address,
		password,
		confirmPassword,
	};

	try {
		const { valid, errors } = validateSignup(newUser);
		if (!valid) return res.status(400).json(errors);

		const checkData = await db.doc(`/users/${email}`).get();
		if (!checkData.exists) {
			const data = await createUserWithEmailAndPassword(
				getAuth(),
				email,
				password
			);

			const token = await data.user.getIdToken();
			const cookieOptions = {
				maxAge: 5 * 24 * 60 * 60 * 1000,
				httpOnly: false,
			};

			res.cookie("authToken", token, cookieOptions);

			const uid = data.user.uid;
			const userCredentials = {
				uid,
				name: newUser.name,
				email: newUser.email,
				address: newUser.address,
				createdAt: new Date().toISOString(),
			};
			await db.doc(`/users/${email}`).set(userCredentials);
			await updateProfile(getAuth().currentUser, {
				displayName: newUser.name,
				photoURL:
					"https://storage.googleapis.com/my-first-project-be65d.appspot.com/avatar.png",
			});

			return res.status(201).json({ token });
		}
	} catch (error) {
		error.message === "Firebase: Error (auth/email-already-in-use)."
			? res.status(400).json({ message: "Email already exists" })
			: res.status(500).json({ message: error.message });
	}
};

exports.loginController = async (req, res) => {
	const { email, password } = req.body;
	const userLogin = {
		email,
		password,
	};
	try {
		const { valid, errors } = validateLogin(userLogin);
		if (!valid) return res.status(400).json(errors);

		const data = await signInWithEmailAndPassword(getAuth(), email, password);

		const token = await data.user.getIdToken();
		const cookieOptions = {
			maxAge: 5 * 24 * 60 * 60 * 1000,
			httpOnly: false,
		};

		res.status(201).cookie("authToken", token, cookieOptions).json({ token });
	} catch (error) {
		error.message === "Firebase: Error (auth/wrong-password)."
			? res.status(403).json({ message: "Wrong password, please try again" })
			: res.status(500).json({ message: error.message });
	}
};

exports.logoutController = (req, res) => {
	res
		.status(200)
		.clearCookie("authToken")
		.json({ message: "Logout successfully!" });
};

exports.resetPasswordController = async (req, res) => {
	const email = req.user.email;
	try {
		await sendPasswordResetEmail(getAuth(), email);
		res.status(202).clearCookie("authToken").json({ message: "Email sent!" });
	} catch (error) {
		res.status(400).json({ message: error.message });
	}
};

exports.verifyEmailController = async (req, res) => {
	try {
		let errors = {};
		const { emailVerified } = await getAuth().currentUser;

		if (!emailVerified) {
			await sendEmailVerification(getAuth().currentUser);

			res.status(202).json({ message: "Email sent!" });
		} else {
			errors.email = "Email has been verified";
		}
		Object.keys(errors).length > 0 && res.status(400).json(errors);
	} catch (error) {
		res.status(400).json({ message: error.message });
	}
};

exports.getProfileController = async (req, res) => {
	try {
		const { uid, email, emailVerified, displayName, isAnonymous, photoURL } =
			await getAuth().currentUser;

		const user = {
			uid,
			email,
			address: req.user.address,
			emailVerified,
			displayName,
			isAnonymous,
			photoURL,
		};
		res.status(200).json({
			user,
		});
	} catch (error) {
		res.status(500).json({ message: error.message });
	}
};

exports.updateProfileController = async (req, res) => {
	const { name, address } = req.body;
	try {
		const datas = {
			name,
			address,
		};

		const user = await db.doc(`/users/${req.user.email}`);
		user.update(datas);

		await updateProfile(getAuth().currentUser, {
			displayName: name,
		});

		res.status(200).json({
			message: "Data has been updated!",
		});
	} catch (error) {
		res.status(400).json({ message: error.message });
	}
};

exports.photoController = async (req, res) => {
	const Busboy = require("busboy");
	const path = require("path");
	const os = require("os");
	const fs = require("fs");

	const busboy = Busboy({ headers: req.headers });

	let imageToBeUploaded = {};
	let imageFileName;
	// String for image token
	let generatedToken = uuidv4();

	busboy.on("file", async (fieldname, file, fileinfo) => {
		const { filename, encoding, mimeType } = fileinfo;
		if (mimeType !== "image/jpeg" && mimeType !== "image/png") {
			return res.status(400).json({ error: "Wrong file type submitted" });
		}
		// my.image.png => ['my', 'image', 'png']
		const imageExtension = filename.split(".")[filename.split(".").length - 1];
		// 32756238461724837.png
		imageFileName = `${Math.round(
			Math.random() * 1000000000000
		).toString()}.${imageExtension}`;
		const filepath = path.join(os.tmpdir(), imageFileName);
		imageToBeUploaded = { filepath, mimeType };
		file.pipe(fs.createWriteStream(filepath));
	});
	busboy.on("finish", async () => {
		try {
			await admin
				.storage()
				.bucket()
				.upload(imageToBeUploaded.filepath, {
					destination: `photoUser/${imageFileName}`,
					resumable: false,
					metadata: {
						contentType: imageToBeUploaded.mimeType,
						firebaseStorageDownloadTokens: generatedToken,
					},
				});

			const imageUrl = `https://firebasestorage.googleapis.com/v0/b/${firebaseConfig.storageBucket}/o/photoUser%2F${imageFileName}?alt=media&token=${generatedToken}`;

			await updateProfile(getAuth().currentUser, { photoURL: imageUrl });

			return res.json({ message: "Image uploaded successfully" });
		} catch (err) {
			console.error(err);
			return res.status(500).json({ error: "Something went wrong" });
		}
	});
	busboy.end(req.rawBody);
};
