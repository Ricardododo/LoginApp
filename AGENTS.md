# AGENTS.md - Development Guidelines

This project is a hybrid application with two components:
- **Backend**: Node.js/Express API (`mi-backend-login/`)
- **Frontend**: Android Kotlin app (`app/`)

## Build, Lint, and Test Commands

### Backend (Node.js/Express)

```bash
# Navigate to backend directory
cd mi-backend-login

# Install dependencies
npm install

# Run development server (with nodemon auto-reload)
npm start

# Run server manually
node server.js

# Run a single test (if tests exist)
npm test -- --testNamePattern="test name"

# Run all tests
npm test
```

### Frontend (Android/Kotlin)

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run lint analysis
./gradlew lint

# Run a single test (unit tests)
./gradlew test --tests "com.ricardo.loginapp.ExampleUnitTest.testName"

# Run a single instrumented test
./gradlew connectedAndroidTest --tests "com.ricardo.loginapp.ExampleInstrumentedTest.testName"

# Run all unit tests
./gradlew test

# Run all tests (unit + instrumented)
./gradlew test connectedAndroidTest

# Clean build
./gradlew clean

# Check dependencies for updates
./gradlew dependencyUpdates
```

## Code Style Guidelines

### General Principles

- Write clean, readable code with meaningful variable and function names
- Keep functions small and focused (single responsibility)
- Add proper error handling at all layers
- Never expose secrets, keys, or passwords in code or logs

---

### Backend (JavaScript/Node.js)

#### Imports
```javascript
// Use require for CommonJS (project uses "type": "commonjs")
const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

// Group imports: external libs, then internal modules
// const internalModule = require('./path/to/module');
```

#### Naming Conventions
- Variables/functions: `camelCase` (e.g., `userName`, `getUserById`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_COUNT`)
- Classes: `PascalCase` (e.g., `UserController`)
- Files: `kebab-case.js` (e.g., `user-service.js`)

#### Formatting
- Use 2 spaces for indentation
- Use single quotes for strings
- Always use semicolons
- Max line length: 100 characters
- Use template literals for string interpolation

#### Error Handling
```javascript
// Always use try-catch for async operations
try {
    const result = await someAsyncFunction();
} catch (error) {
    console.error('Error description:', error.message);
    return res.status(500).json({ message: 'User-friendly error message' });
}

// Validate inputs at the start of functions
if (!requiredParam) {
    return res.status(400).json({ message: 'Validation error message' });
}

// Never expose internal error details to clients
// Good: res.status(500).json({ message: 'Error en el servidor' })
// Bad: res.status(500).json({ message: error.stack })
```

#### API Response Format
```javascript
// Success responses
res.status(200).json({ data: { ... } });
res.status(201).json({ message: 'Resource created', data: { ... } });

// Error responses (never expose sensitive data)
res.status(400).json({ message: 'Validation failed' });
res.status(401).json({ message: 'Unauthorized' });
res.status(404).json({ message: 'Resource not found' });
res.status(500).json({ message: 'Internal server error' });
```

#### Security
- Never hardcode secrets; use environment variables
- Validate and sanitize all user inputs
- Use parameterized queries (if using a database)
- Implement rate limiting for authentication endpoints

---

### Frontend (Kotlin/Android)

#### Imports
```kotlin
// Group imports: android, androidx, third-party, project
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ricardo.loginapp.viewmodel.UserViewModel
```

#### Naming Conventions
- Classes: `PascalCase` (e.g., `MainActivity`, `UserRepository`)
- Functions/variables: `camelCase` (e.g., `userName`, `getUserData()`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_COUNT`)
- Resource files: `snake_case` (e.g., `activity_main.xml`, `user_item.xml`)

#### Formatting
- Use 4 spaces for indentation (Kotlin default)
- Follow Kotlin coding conventions
- Keep line length under 120 characters when possible
- Use meaningful names for all identifiers

#### Types
- Use Kotlin's type system effectively; avoid `Any` when possible
- Use nullable types (`String?`) when a value can be null
- Prefer `val` over `var`; use `var` only when mutation is necessary

#### Error Handling
```kotlin
// Use try-catch for operations that can fail
try {
    val result = networkCall()
} catch (e: NetworkException) {
    // Handle specific exception types
    showErrorMessage("Network error: ${e.message}")
} catch (e: Exception) {
    // Catch generic exceptions last
    Log.e("TAG", "Unexpected error", e)
}

// Use Result<T> for functions that can fail
fun fetchUser(): Result<User> {
    return try {
        val user = api.getUser()
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

#### UI Development
- Use ViewBinding or DataBinding
- Follow Material Design guidelines
- Handle loading and error states in UI
- Use proper lifecycle-aware components

---

### Git Conventions

- Use meaningful commit messages: `feat: add user login endpoint`
- Commit types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
- Keep commits atomic (one logical change per commit)

---

### Testing Guidelines

#### Backend Tests
- Use Jest or Mocha for testing
- Group tests with `describe` blocks
- Use clear test names: `should return 401 for invalid credentials`
- Test both success and failure paths

#### Android Tests
- Unit tests: Use JUnit 4/5 with Kotlin
- Instrumented tests: Use Espresso for UI testing
- Test naming: `testMethodName_Scenario_ExpectedResult`
- Keep tests independent; mock external dependencies

---

### File Organization

```
Backend:
├── server.js           # Entry point
├── routes/             # API route handlers
├── controllers/        # Business logic
├── models/             # Data models
├── middleware/         # Custom middleware
└── utils/              # Helper functions

Android:
├── data/               # Repositories, data sources
├── domain/             # Use cases, business logic
├── ui/                 # Activities, Fragments, ViewModels
├── di/                 # Dependency injection
└── util/               # Utilities
```

---

### Dependencies

- Keep dependencies up to date
- Avoid adding unnecessary dependencies
- Use well-maintained, stable libraries
- Pin dependency versions for production
