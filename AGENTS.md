# AGENTS.md - Guías de Desarrollo

Este proyecto es una aplicación híbrida con dos componentes:
- **Backend**: API Node.js/Express (`mi-backend-login/`)
- **Frontend**: Aplicación Android Kotlin (`app/`)

## Comandos de Build, Lint y Pruebas

### Backend (Node.js/Express)

```bash
# Navegar al directorio del backend
cd mi-backend-login

# Instalar dependencias
npm install

# Ejecutar servidor en desarrollo (con nodemon auto-reload)
npm start

# Ejecutar servidor manualmente
node server.js

# Ejecutar una prueba individual (si existen pruebas)
npm test -- --testNamePattern="nombre de la prueba"

# Ejecutar todas las pruebas
npm test
```

### Frontend (Android/Kotlin)

```bash
# Construir APK de depuración
./gradlew assembleDebug

# Construir APK de lanzamiento
./gradlew assembleRelease

# Ejecutar análisis de lint
./gradlew lint

# Ejecutar una prueba unitaria individual
./gradlew test --tests "com.ricardo.loginapp.ExampleUnitTest.nombreTest"

# Ejecutar una prueba instrumentada individual
./gradlew connectedAndroidTest --tests "com.ricardo.loginapp.ExampleInstrumentedTest.nombreTest"

# Ejecutar todas las pruebas unitarias
./gradlew test

# Ejecutar todas las pruebas (unitarias + instrumentadas)
./gradlew test connectedAndroidTest

# Limpiar build
./gradlew clean

# Verificar actualizaciones de dependencias
./gradlew dependencyUpdates
```

## Guías de Estilo de Código

### Principios Generales

- Escribir código limpio y legible con nombres significativos de variables y funciones
- Mantener funciones pequeñas y enfocadas (responsabilidad única)
- Agregar manejo de errores adecuado en todas las capas
- Nunca exponer secretos, claves o contraseñas en código o logs

---

### Backend (JavaScript/Node.js)

#### Importaciones
```javascript
// Usar require para CommonJS (el proyecto usa "type": "commonjs")
const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

// Agrupar importaciones: libs externas, luego módulos internos
// const internalModule = require('./path/to/module');
```

#### Convenciones de Nombres
- Variables/funciones: `camelCase` (ej., `userName`, `getUserById`)
- Constantes: `UPPER_SNAKE_CASE` (ej., `MAX_RETRY_COUNT`)
- Clases: `PascalCase` (ej., `UserController`)
- Archivos: `kebab-case.js` (ej., `user-service.js`)

#### Formato
- Usar 2 espacios para indentación
- Usar comillas simples para strings
- Siempre usar punto y coma
- Longitud máxima de línea: 100 caracteres
- Usar template literals para interpolación de strings

#### Manejo de Errores
```javascript
// Siempre usar try-catch para operaciones async
try {
    const result = await someAsyncFunction();
} catch (error) {
    console.error('Descripción del error:', error.message);
    return res.status(500).json({ message: 'Mensaje de error amigable' });
}

// Validar entradas al inicio de las funciones
if (!requiredParam) {
    return res.status(400).json({ message: 'Mensaje de validación' });
}

// Nunca exponer detalles internos de errores a los clientes
// Bien: res.status(500).json({ message: 'Error en el servidor' })
// Mal: res.status(500).json({ message: error.stack })
```

#### Formato de Respuesta de API
```javascript
// Respuestas exitosas
res.status(200).json({ data: { ... } });
res.status(201).json({ message: 'Recurso creado', data: { ... } });

// Respuestas de error (nunca exponer datos sensibles)
res.status(400).json({ message: 'Validación fallida' });
res.status(401).json({ message: 'No autorizado' });
res.status(404).json({ message: 'Recurso no encontrado' });
res.status(500).json({ message: 'Error interno del servidor' });
```

#### Seguridad
- Nunca hardcodear secretos; usar variables de entorno
- Validar y sanitizar todas las entradas de usuario
- Usar consultas parametrizadas (si se usa base de datos)
- Implementar rate limiting para endpoints de autenticación

---

### Frontend (Kotlin/Android)

#### Importaciones
```kotlin
// Agrupar importaciones: android, androidx, third-party, proyecto
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ricardo.loginapp.viewmodel.UserViewModel
```

#### Convenciones de Nombres
- Clases: `PascalCase` (ej., `MainActivity`, `UserRepository`)
- Funciones/variables: `camelCase` (ej., `userName`, `getUserData()`)
- Constantes: `UPPER_SNAKE_CASE` (ej., `MAX_RETRY_COUNT`)
- Archivos de recursos: `snake_case` (ej., `activity_main.xml`, `user_item.xml`)

#### Formato
- Usar 4 espacios para indentación (default de Kotlin)
- Seguir convenciones de código de Kotlin
- Mantener longitud de línea bajo 120 caracteres cuando sea posible
- Usar nombres significativos para todos los identificadores

#### Tipos
- Usar efectivamente el sistema de tipos de Kotlin; evitar `Any` cuando sea posible
- Usar tipos nullable (`String?`) cuando un valor puede ser null
- Preferir `val` sobre `var`; usar `var` solo cuando sea necesario mutación

#### Manejo de Errores
```kotlin
// Usar try-catch para operaciones que pueden fallar
try {
    val result = networkCall()
} catch (e: NetworkException) {
    // Manejar tipos de excepción específicos
    showErrorMessage("Error de red: ${e.message}")
} catch (e: Exception) {
    // Capturar excepciones genéricas al final
    Log.e("TAG", "Error inesperado", e)
}

// Usar Result<T> para funciones que pueden fallar
fun fetchUser(): Result<User> {
    return try {
        val user = api.getUser()
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

#### Desarrollo de UI
- Usar ViewBinding o DataBinding
- Seguir guías de Material Design
- Manejar estados de carga y error en la UI
- Usar componentes conscientes del ciclo de vida

---

### Convenciones de Git

- Usar mensajes de commit significativos: `feat: agregar endpoint de login`
- Tipos de commit: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
- Mantener commits atómicos (un cambio lógico por commit)

---

### Guías de Pruebas

#### Pruebas de Backend
- Usar Jest o Mocha para pruebas
- Agrupar pruebas con bloques `describe`
- Usar nombres claros: `should return 401 for invalid credentials`
- Probar tanto rutas de éxito como de falla

#### Pruebas de Android
- Pruebas unitarias: Usar JUnit 4/5 con Kotlin
- Pruebas instrumentadas: Usar Espresso para pruebas de UI
- Nombres de pruebas: `testMethodName_Escenario_ResultadoEsperado`
- Mantener pruebas independientes; mockear dependencias externas

---

### Organización de Archivos

```
Backend:
├── server.js           # Punto de entrada
├── routes/             # Manejadores de rutas de API
├── controllers/        # Lógica de negocio
├── models/            # Modelos de datos
├── middleware/        # Middleware personalizado
└── utils/             # Funciones helper

Android:
├── data/              # Repositorios, fuentes de datos
├── domain/            # Casos de uso, lógica de negocio
├── ui/                # Activities, Fragments, ViewModels
├── di/                # Inyección de dependencias
└── util/              # Utilidades
```

---

### Dependencias

- Mantener dependencias actualizadas
- Evitar agregar dependencias innecesarias
- Usar librerías bien mantenidas y estables
- Fijar versiones de dependencias para producción
