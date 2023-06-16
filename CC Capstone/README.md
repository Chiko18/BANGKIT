# Plastic Point API Beta Documentation

This document provides information on how to use the Plastic Point API Beta. The API allows users to perform various operations related to user management and profile management. The API is built using Node.js, Express, and Firebase technologies.

## Base URL

The base URL for all API endpoints is: `https://api-ros2ig4wta-uc.a.run.app/`

## Sign Up

**URL**: `/signup`

**Method**: `POST`

**Input**:
```json
{
    "name": "user",
    "email": "khfdasd@gmail.com", // email format
    "address": "jl kucing", // no specific requirements at the moment
    "password": "Abc12345", // minimum 8 characters, at least 1 uppercase letter, 1 lowercase letter, and 1 digit
    "confirmPassword": "Abc12345"
}
```

**Output**: The output token should be sent in the `req.headers.authorization` header with the format `Bearer $token`.
```json
{
    "token": "eyJh..."
}
```

## Log In

**URL**: `/login`

**Method**: `POST`

**Input**:
```json
{
    "email": "khfdasd@gmail.com", // email format
    "password": "Abc12345" // minimum 8 characters, at least 1 uppercase letter, 1 lowercase letter, and 1 digit
}
```

**Output**: The output token should be sent in the `req.headers.authorization` header with the format `Bearer $token`.
```json
{
    "token": "eyJh..."
}
```

## Reset Password

**URL**: `/reset-password`

**Method**: `POST`

**Input**: No input required.

**Output**:
```json
{
    "message": "Email sent!"
}
```

## Verify Email

**URL**: `/verify-email`

**Method**: `POST`

**Input**: No input required.

**Output**:
```json
{
    "message": "Email sent!"
}
```

## Get User Profile

**URL**: `/profile`

**Method**: `GET`

**Input**: No input required.

**Output**:
```json
{
    "user": {
        "uid": "MBlrejGpPtb2kVdlH6UqRhJC5dD3",
        "email": "khafiasdx@gmail.com",
        "address": "jawa",
        "emailVerified": false,
        "displayName": "eko",
        "isAnonymous": false,
        "photoURL": "https://storage.googleapis.com/my-first-project-be65d.appspot.com/avatar.png"
    }
}
```

## Logout

**URL**: `/logout`

**Method**: `POST`

**Input**: No input required.

**Output**:
```json
{
    "message": "Logout successfully!"
}
```

## Update User Profile

**URL**: `/profile`

**Method**: `PUT`

**Input**:
```json
{
    "name": "<name>",
    "address": "<address>"
}
```

**Output**:
```json
{
    "message": "Data has been updated!"
}
```

## Upload Image

**URL**: `/upload`

**Method**: `POST`

**Input**: Attach an image file (e.g., image.jpg or image.png).

**Output**:
```json
{
    "message": "Image uploaded successfully"
}
```

## Technologies Used

The Plastic Point API Beta is built using the following technologies:
- Node.js
- Express
- Firebase Admin
- Firebase
- Busboy
- Cookie Parser
- Firebase Functions
-

 Firebase Storage

Please refer to the official documentation of these technologies for more details on how to use them.
