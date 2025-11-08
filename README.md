# Examen Final - Sistema de Inventario

Este es un sistema de backend para gestionar el inventario de una pequeña empresa de importaciones.

## Análisis y Diseño

### Diagrama ER (Entidad-Relación)

```
+-----------+       +-----------+       +------------+
| Proveedor | 1--<| Producto  | 1--<| Movimiento |
+-----------+       +-----------+       +------------+
| id        |       | id        |       | id         |
| nombre    |       | nombre    |       | fecha      |
| direccion |       | descrip   |       | tipo       |
| telefono  |       | existencia|       | cantidad   |
+-----------+       | precio    |       | producto_id|
                    | proveedor |       +------------+
                    +-----------+
```

### Entidades y Relaciones

*   **Proveedor:** Almacena la información de los proveedores.
    *   Un `Proveedor` puede tener muchos `Productos`.
*   **Producto:** Almacena la información de los productos.
    *   Un `Producto` pertenece a un solo `Proveedor`.
    *   Un `Producto` puede tener muchos `Movimientos`.
*   **Movimiento:** Registra las entradas y salidas de inventario.
    *   Un `Movimiento` pertenece a un solo `Producto`.

## Instrucciones para Ejecutar el Proyecto

### Prerrequisitos

*   Java 17 o superior
*   Maven
*   PostgreSQL

### 1. Configurar la Base de Datos

1.  Asegúrate de tener PostgreSQL instalado y en ejecución.
2.  Crea una base de datos llamada `examenfinal`.
3.  Abre el archivo `src/main/resources/application.properties` y configura las siguientes propiedades con tus credenciales de PostgreSQL:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/examenfinal
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    ```

### 2. Ejecutar la Aplicación

Puedes ejecutar la aplicación de dos maneras:

**a) Usando el Maven Wrapper (recomendado):**

En la raíz del proyecto, ejecuta el siguiente comando:

```bash
./mvnw spring-boot:run
```
o en Windows:
```bash
.\mvnw.cmd spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`.

**b) Usando Maven:**

Si tienes Maven instalado globalmente, puedes usar:

```bash
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`.

### 3. Datos de Prueba

La base de datos se inicializará automáticamente con los siguientes datos de prueba gracias al archivo `src/main/resources/data.sql`:
*   3 Proveedores
*   5 Productos
*   10 Movimientos (5 de entrada y 5 de salida)

## Endpoints de la API

La colección de Postman se puede encontrar en el siguiente enlace:
*   [TODO: Añadir enlace a la colección de Postman]

### Proveedores (`/api/proveedores`)

*   `GET /`: Obtiene todos los proveedores.
*   `GET /{id}`: Obtiene un proveedor por su ID.
*   `POST /`: Crea un nuevo proveedor.
*   `PUT /{id}`: Actualiza un proveedor existente.
*   `DELETE /{id}`: Elimina un proveedor.

### Productos (`/api/productos`)

*   `GET /`: Obtiene todos los productos.
*   `GET /{id}`: Obtiene un producto por su ID.
*   `POST /`: Crea un nuevo producto.
*   `PUT /{id}`: Actualiza un producto existente.
*   `DELETE /{id}`: Elimina un producto.

### Movimientos (`/api/movimientos`)

*   `GET /`: Obtiene todos los movimientos.
*   `POST /`: Crea un nuevo movimiento (entrada o salida).
    *   **Cuerpo del Request (Ejemplo):**
        ```json
        {
          "idProducto": 1,
          "cantidad": 10,
          "tipo": "SALIDA"
        }
        ```

