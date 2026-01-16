# Sistema de Gestión de Productos – Spring Boot

Proyecto desarrollado con **Spring Boot** que implementa una API REST para la gestión de **productos**, **usuarios** y **categorías**, aplicando buenas prácticas de arquitectura por capas, validaciones de negocio y relaciones entre entidades usando **JPA/Hibernate**.

Este proyecto forma parte de un trabajo académico y está diseñado para ser claro, mantenible y escalable.

---

## Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- Base de datos relacional (H2 / PostgreSQL / MySQL)
- Git y GitHub

---

## Arquitectura del proyecto

El proyecto sigue una **arquitectura por capas**, separando responsabilidades de forma clara:


Esto permite:
- Separación de responsabilidades
- Código más mantenible
- Facilidad para testing
- Escalabilidad del sistema

---

## Modelo de datos

### Entidades principales

- **User**
- **Product**
- **Category**

### Relaciones implementadas

- Un **usuario** puede tener muchos **productos** (1:N)
- Un **producto** puede pertenecer a **múltiples categorías** (N:N)
- Una **categoría** puede estar asociada a **muchos productos**

---

## Endpoints principales

### Productos

| Método | Endpoint | Descripción |
|------|---------|-------------|
| POST | `/api/products` | Crear un producto |
| GET | `/api/products` | Listar todos los productos |
| GET | `/api/products/{id}` | Obtener producto por ID |
| GET | `/api/products/user/{userId}` | Productos por usuario |
| GET | `/api/products/category/{categoryId}` | Productos por categoría |
| PUT | `/api/products/{id}` | Actualizar producto |
| PATCH | `/api/products/{id}` | Actualización parcial |
| DELETE | `/api/products/{id}` | Eliminar producto |

---

## Ejemplo de creación de producto

### Request (JSON)

```json
{
  "name": "Laptop Lenovo",
  "description": "Laptop para desarrollo",
  "price": 850.50,
  "userId": 1,
  "categoryIds": [1, 2]
}

{
  "id": 10,
  "name": "Laptop Lenovo",
  "price": 850.5,
  "description": "Laptop para desarrollo",
  "user": {
    "id": 1,
    "name": "Juan Pérez",
    "email": "juan@email.com"
  },
  "categories": [
    {
      "id": 1,
      "name": "Tecnología",
      "description": "Productos tecnológicos"
    }
  ],
  "createdAt": "2026-01-10T10:15:30",
  "updatedAt": "2026-01-10T10:15:30"
}
