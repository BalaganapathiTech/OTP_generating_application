# OTP_generating_application

A Spring Boot application that provides **OTP-based authentication** with **JWT token generation**, integrated with **Twilio SMS service** and a responsive **Thymeleaf frontend**.

---

## Table of Contents

* [Features](#features)
* [Tech Stack](#tech-stack)
* [Setup & Installation](#setup--installation)
* [Configuration](#configuration)
* [Running the Application](#running-the-application)
* [API Endpoints](#api-endpoints)
* [Frontend](#frontend)
* [JWT Usage](#jwt-usage)
* [Limitations](#limitations)
* [Future Improvements](#future-improvements)
* [License](#license)

---

## Features

* Request OTP for a phone number via Twilio SMS
* Verify OTP and generate **JWT** token
* Responsive HTML + CSS UI using **Thymeleaf**
* JSON-based API responses
* JWT-based authentication for securing APIs
* Cross-Origin support (`@CrossOrigin`) for frontend integration

---

## Tech Stack

* **Backend:** Spring Boot, Spring Security, JWT, Java 17+
* **Frontend:** Thymeleaf, HTML5, CSS3, JavaScript
* **Messaging:** Twilio SMS API (OTP delivery)
* **Build Tool:** Maven
* **Database:** In-memory (for OTP cache) or can be extended

---

## Setup & Installation

1. **Clone the repository**:

```bash
git clone https://github.com/yourusername/SecureAuth.git
cd SecureAuth
```

2. **Install dependencies**:

```bash
mvn clean install
```

3. **Configure Twilio credentials**:

* Open `application.properties` and set:

```properties
twilio.accountSid=YOUR_TWILIO_ACCOUNT_SID
twilio.authToken=YOUR_TWILIO_AUTH_TOKEN
twilio.phoneNumber=YOUR_TWILIO_PHONE_NUMBER
jwt.secret=YOUR_SECRET_KEY
```

---

## Configuration

* **JWT Secret:** Used to sign tokens
* **Twilio:** Used to send OTP SMS
* **CORS:** Enabled via `@CrossOrigin` in the controller

> For trial Twilio accounts, you must **verify recipient phone numbers** before sending OTP.

---

## Running the Application

1. Run the Spring Boot application:

```bash
mvn spring-boot:run
```

2. Open the application in a browser:

```
http://localhost:8080/otp
```

* Enter phone number → request OTP → enter OTP → verify
* Successful verification stores **JWT** in browser `localStorage`

---

## API Endpoints

| Endpoint                                | Method | Description                      |
| --------------------------------------- | ------ | -------------------------------- |
| `/api/client/auth/requestOtp/{phoneNo}` | GET    | Request OTP for the phone number |
| `/api/client/auth/verifyOtp`            | POST   | Verify OTP and return JWT        |
| `/api/client/auth/hello`                | GET    | Test endpoint                    |

**Request Body Example for Verify OTP:**

```json
{
    "phoneNo": "+917339299640",
    "otp": "123456"
}
```

**Response Example:**

```json
{
    "status": "success",
    "message": "OTP verified successfully",
    "jwt": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## Frontend

* `otp.html` located in `src/main/resources/templates/`
* Responsive UI with mobile-first design
* Two main actions:

  * Request OTP
  * Verify OTP
* Displays success/error messages dynamically
* Stores JWT in `localStorage` for API calls

---

## JWT Usage

* JWT is generated upon OTP verification
* Store JWT in browser for authentication in future requests:

```javascript
localStorage.setItem("jwt", data.jwt);
```

* Use JWT in API calls:

```javascript
fetch("/api/protected-endpoint", {
    headers: {
        "Authorization": "Bearer " + localStorage.getItem("jwt")
    }
});
```

---

## Limitations

* **Trial Twilio account:** Only sends OTP to verified numbers
* OTP is stored in-memory → not persistent
* No password-based login implemented
* Minimal error handling for demonstration purposes

---

## Future Improvements

* Add **persistent database** to store users and OTP history
* Add **password + OTP login**
* Add **refresh JWT token** mechanism
* Add **role-based authorization**
* Add **modern frontend** with React or Angular
* Use **rate limiting** for OTP requests

---

## License

This project is open-source and available under the **MIT License**.
