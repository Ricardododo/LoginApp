# LoginApp

Una aplicación moderna de inicio de sesión y registro para Android con un backend en Node.js/Express.

## Descripción del Proyecto

LoginApp es una aplicación móvil de pila completa que proporciona autenticación segura de usuarios. El frontend de Android cuenta con diseños de UI modernos e intuitivos para las pantallas de inicio de sesión y registro, mientras que el backend proporciona una API REST con base de datos SQLite para almacenamiento persistente.

## Características

- Registro de usuarios con nombre, email y contraseña
- Inicio de sesión seguro con JWT
- Hash de contraseñas con bcrypt
- UI moderna con Material Design
- Validación de entrada
- Manejo de errores
- API RESTful

## Tecnologías Utilizadas

### Frontend (Android)
- **Lenguaje**: Kotlin
- **Arquitectura**: MVVM-ready con Clean Architecture
- **UI**: Layouts XML con Componentes Material Design
- **Redes**: Retrofit 2 + OkHttp
- **Async**: Kotlin Coroutines
- **JSON**: Gson

### Backend
- **Runtime**: Node.js
- **Framework**: Express.js
- **Base de datos**: SQLite
- **Autenticación**: JWT (JSON Web Tokens)
- **Seguridad de contraseñas**: bcrypt

## Estructura del Proyecto

```
LoginApp/
├── app/                      # Aplicación Android
│   ├── src/main/
│   │   ├── java/com/ricardo/loginapp/
│   │   │   ├── api/          # Cliente Retrofit
│   │   │   ├── model/        # Modelos de datos
│   │   │   └── ui/           # Activities
│   │   └── res/              # Recursos (layouts, drawables, values)
│   └── build.gradle.kts
├── mi-backend-login/         # Backend Node.js
│   ├── model/                # Modelos de base de datos
│   ├── database.js           # Conexión SQLite
│   └── server.js             # Servidor Express
├── gradle/                   # Wrapper de Gradle
└── README.md
```

## Primeros Pasos

### Requisitos Previos

- Android Studio Arctic Fox o posterior
- Node.js 14+ 
- npm o yarn

### Configuración del Backend

```bash
cd mi-backend-login
npm install
npm start
```

El servidor se ejecutará en `http://localhost:3004`

### Configuración de Android

1. Abre el proyecto en Android Studio
2. Espera a que Gradle sincronice
3. Ejecuta la app en un emulador o dispositivo

## Endpoints de la API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/register` | Registrar un nuevo usuario |
| POST | `/api/login` | Iniciar sesión y obtener token JWT |

### Solicitud de Registro
```json
{
  "nombre": "Juan Perez",
  "email": "juan@ejemplo.com",
  "password": "password123"
}
```

### Solicitud de Inicio de Sesión
```json
{
  "email": "juan@ejemplo.com",
  "password": "password123"
}
```

### Respuesta de Inicio de Sesión
```json
{
  "message": "Login exitoso",
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "user": {
    "id": 1,
    "nombre": "Juan Perez",
    "email": "juan@ejemplo.com"
  }
}
```

## Capturas de Pantalla

La app cuenta con diseños modernos de Material Design:
- Pantalla de inicio de sesión con campos de email y contraseña
- Pantalla de registro con campos de nombre, email, contraseña y confirmar contraseña
- Validación de entrada con mensajes de error
- Estados de carga
- Notificaciones toast

## Construcción

### APK de Depuración Android

```bash
./gradlew assembleDebug```

El APK se generará en `app/build/outputs/apk/debug/app-debug.apk`

## Licencia

Licencia ISC
