# 1. Registro de usuarios
- http://localhost:8080/api/auth/register

```
    {
    "username": "anxo",
    "email": "anxo.campos260204@gmail.com",
    "password": "Ad1234",
    "roles": ["ADMIN"],
    "imageUrl": "1000100074.jpg"
    }
```

# 2. Login de usuarios
- http://localhost:8080/api/auth/login

```
    {
    "username": "user",
    "password": "Ad1234"
    }
```

# 3. Crear categoria
- http://localhost:8080/api/categorias
  
  ```
    {
        "name":"Informatica",
        "imageUrl": "imagen.jpg
    }
  ```

# 4. Crear producto
```
- http://localhost:8080/api/productos
  
  {
  "name": "Laptop Lenovo",
  "description": "14 pulgadas, 16GB RAM",
  "price": 800.0,
  "stock": 5,
  "imageUrl": "https://www.tuexperto.com/wp-content/uploads/2017/11/a_fondo_Lenovo_Legion_Y920_03.jpg",
  "categoryId": 1
}
```

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

