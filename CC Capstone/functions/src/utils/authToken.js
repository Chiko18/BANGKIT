const { admin, db } = require("../utils/admin.js");

exports.authToken = async (req, res, next) => {
	let idToken =
		req.headers.authorization && req.headers.authorization.startsWith("Bearer ")
			? req.headers.authorization.split("Bearer ")[1]
			: res.status(401).json({
					message: "Unauthorized",
			  });

	try {
		const decodedToken = await admin.auth().verifyIdToken(idToken);
		req.user = decodedToken;
		const userName = await db
			.collection("users")
			.where("uid", "==", req.user.uid)
			.limit(1)
			.get();
		req.user = userName.docs[0].data();
		next();
	} catch (error) {
		res.status(401).json({
			message: "Token not valid",
		});
	}
};
