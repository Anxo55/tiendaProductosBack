# Registro de usuarios
- http://localhost:8080/api/auth/register

    {
    "username": "uesr",
    "email": "user@gmail.com",
    "password": "Ad1234",
    "roles": ["USER"]
    }


# Login de usuarios
- http://localhost:8080/api/auth/login

    {
    "username": "user",
    "password": "Ad1234"
    }

# Crear categoria
- http://localhost:8080/api/categorias
  
    {
        "name":"Informatica"
    }

# Crear producto
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
