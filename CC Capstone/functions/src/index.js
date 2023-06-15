const { onRequest } = require("firebase-functions/v2/https");
const express = require("express");
const cors = require("cors");
const cookieParser = require("cookie-parser");

const {
	signupController,
	loginController,
	logoutController,
	resetPasswordController,
	verifyEmailController,
	getProfileController,
	updateProfileController,
	photoController,
} = require("./handlers/users.js");

const { authToken } = require("./utils/authToken.js");

const app = express();
app.use(cors());
app.use(cookieParser());

// Users
app.post("/signup", signupController); // Signup Email
app.post("/login", loginController); // login Email
app.post("/logout", authToken, logoutController); // Logout
app.post("/reset-password", authToken, resetPasswordController); // Reset Password
app.post("/verify-email", authToken, verifyEmailController); // Verify Email
app.get("/profile", authToken, getProfileController); // Profile
app.put("/profile", authToken, updateProfileController); // Update Profile
app.post("/upload", authToken, photoController); // Update Photo

exports.api = onRequest(app);
