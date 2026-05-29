# Medellin Painting - Backend API

API REST del backend para **Medellin Painting**. Gestiona proyectos, mensajes de contacto, autenticación y subida de imágenes a Cloudinary.

## Tecnologías

- Java + Spring Boot
- Spring Security + JWT
- Spring Data JPA + PostgreSQL
- Cloudinary (almacenamiento de imágenes)

## Endpoints principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/login` | Autenticación, retorna JWT |
| GET | `/api/public/projects` | Lista proyectos (público) |
| GET | `/api/public/settings` | Configuración del sitio (público) |
| POST | `/api/projects` | Crear proyecto (admin) |
| PUT | `/api/projects/{id}` | Editar proyecto (admin) |
| DELETE | `/api/projects/{id}` | Eliminar proyecto (admin) |
| GET | `/api/messages` | Ver mensajes de contacto (admin) |
| POST | `/api/upload` | Subir imagen a Cloudinary (admin) |

## Instalación

1. Clonar el repositorio
2. Crear la base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE medellin_painting;
   ```
3. Copiar el archivo de configuración:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
4. Editar `application.properties` con tus credenciales de base de datos y Cloudinary
5. Ejecutar:
   ```bash
   ./mvnw spring-boot:run
   ```

El servidor inicia en `http://localhost:8080`

## Usuario por defecto

Al iniciar por primera vez se crea automáticamente un usuario administrador:

- **Usuario:** `admin`
- **Contraseña:** `admin123`

> Se recomienda cambiar la contraseña después del primer acceso.
