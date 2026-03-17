# LoginApp

A modern Android login and registration application with a Node.js/Express backend.

## Project Description

LoginApp is a full-stack mobile application that provides secure user authentication. The Android frontend features modern, intuitive UI designs for login and registration screens, while the backend provides a RESTful API with SQLite database for persistent storage.

## Features

- User registration with name, email, and password
- Secure login with JWT authentication
- Password hashing with bcrypt
- Modern Material Design UI
- Input validation
- Error handling
- RESTful API

## Technology Stack

### Frontend (Android)
- **Language**: Kotlin
- **Architecture**: MVVM-ready with Clean Architecture
- **UI**: XML Layouts with Material Design Components
- **Networking**: Retrofit 2 + OkHttp
- **Async**: Kotlin Coroutines
- **JSON**: Gson

### Backend
- **Runtime**: Node.js
- **Framework**: Express.js
- **Database**: SQLite
- **Authentication**: JWT (JSON Web Tokens)
- **Password Security**: bcrypt

## Project Structure

```
LoginApp/
├── app/                      # Android Application
│   ├── src/main/
│   │   ├── java/com/ricardo/loginapp/
│   │   │   ├── api/          # Retrofit API client
│   │   │   ├── model/        # Data models
│   │   │   └── ui/           # Activities
│   │   └── res/              # Resources (layouts, drawables, values)
│   └── build.gradle.kts
├── mi-backend-login/         # Node.js Backend
│   ├── model/               # Database models
│   ├── database.js           # SQLite connection
│   └── server.js             # Express server
├── gradle/                   # Gradle wrapper
└── README.md
```

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Node.js 14+ 
- npm or yarn

### Backend Setup

```bash
cd mi-backend-login
npm install
npm start
```

The server will run on `http://localhost:3004`

### Android Setup

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Run the app on an emulator or device

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/register` | Register a new user |
| POST | `/api/login` | Login and get JWT token |

### Register Request
```json
{
  "nombre": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

### Login Request
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

### Login Response
```json
{
  "message": "Login exitoso",
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "user": {
    "id": 1,
    "nombre": "John Doe",
    "email": "john@example.com"
  }
}
```

## Screenshots

The app features modern Material Design layouts with:
- Login screen with email and password fields
- Registration screen with name, email, password, and confirm password fields
- Input validation with error messages
- Loading states
- Toast notifications

## Build

### Android Debug APK

```bash
./gradlew assembleDebug
```

The APK will be generated at `app/build/outputs/apk/debug/app-debug.apk`

## License

ISC License
