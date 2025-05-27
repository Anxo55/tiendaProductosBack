# 1. Registro de usuarios
- http://localhost:8080/api/auth/register

    {
    "username": "uesr",
    "email": "user@gmail.com",
    "password": "Ad1234",
    "roles": ["USER"]
    }


# 2. Login de usuarios
- http://localhost:8080/api/auth/login

    {
    "username": "user",
    "password": "Ad1234"
    }

# 3. Crear categoria
- http://localhost:8080/api/categorias
  
    {
        "name":"Informatica"
    }

# 4. Crear producto
- http://localhost:8080/api/productos
  
    {
  "name": "Laptop Dell XPS 13",
  "description": "Laptop ultradelgada con procesador i7 y pantalla 4K",
  "price": 1200.00,
  "stock": 50,
  "category": {
    "id": 1
  }
}

# 5. Obtener lista de productos

GET http://localhost:8080/api/productos

# 6. Carrito de compras

Nota: Para todas las peticiones de carrito se debe enviar el header:
Authorization: Bearer <tu_token_jwt>

# 6.1 Obtener carrito actual del usuario
GET http://localhost:8080/api/carrito

# 6.2 Añadir producto al carrito
POST http://localhost:8080/api/carrito/add?productId=<ID_DEL_PRODUCTO>&quantity=<CANTIDAD>

Ejemplo:

```
POST http://localhost:8080/api/carrito/add?productId=3&quantity=2
```

# 6.3 Eliminar producto del carrito
DELETE http://localhost:8080/api/carrito/remove?productId=<ID_DEL_PRODUCTO>

Ejemplo:

```
DELETE http://localhost:8080/api/carrito/remove?productId=3
```

# 6.4 Vaciar carrito
DELETE http://localhost:8080/api/carrito/clear

# 7. Gestión de usuarios

## 7.1 Listar todos los usuarios
GET http://localhost:8080/api/usuarios

## 7.2 Obtener perfil de un usuario
GET http://localhost:8080/api/usuarios/{id}


## 7.3 Actualizar perfil de usuario
PUT http://localhost:8080/api/usuarios/{id}

Ejemplo body JSON:

```
{
  "username": "user_actualizado",
  "email": "userupdated@gmail.com"
}
```

