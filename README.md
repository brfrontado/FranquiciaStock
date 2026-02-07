El proyecto consiste en el desarrollo de una API RESTful utilizando Java 17 y el framework Spring Boot, orientada a la creación de servicios backend robustos, escalables y de alto rendimiento. La aplicación implementa una arquitectura basada en capas, facilitando el mantenimiento y la extensibilidad del sistema.

Para la persistencia de datos se emplea Spring Data JPA, lo que permite una interacción eficiente con la base de datos mediante el uso de repositorios, reduciendo la necesidad de código boilerplate y aprovechando el mapeo objeto-relacional (ORM). Como sistema de gestión de base de datos se utiliza MySQL, garantizando almacenamiento seguro y confiable de la información.

La API expone endpoints REST que permiten realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar), siguiendo buenas prácticas de desarrollo y estándares HTTP. El proyecto está configurado para facilitar la integración con aplicaciones frontend o servicios externos, asegurando una comunicación clara y eficiente entre sistemas.


## Funcionalidades

Listar todos los productos
Crear un nuevo producto
Actualizar un producto existente
Eliminar un producto por ID
Validar nombre duplicado en creación

- Método	     URL	                Descripción
- GET	    /api/v1/productos	        Listar todos los productos
- POST	    /api/v1/productos	         Crear un nuevo producto
- PUT	    /api/v1/productos{id}	        Actualizar un producto
- DELETE	/api/v1/productos/{id}	    Eliminar un producto por ID
**************************************************************************
GET	        /api/v1/franquicias	        Listar todos los productos
- POST	    /api/v1/franquicias	         Crear un nuevo producto
- PUT	    /api/v1/franquicias{id}	        Actualizar un producto
- DELETE	/api/v1/franquicias/{id}	    Eliminar un producto por ID
  
************************************************************************
- GET	     /api/v1/sucursales	        Listar todos los productos
- POST	    /api/v1/sucursales	         Crear un nuevo producto
- PUT	    /api/v1/sucursales{id}        Actualizar un producto
- DELETE	/api/v1/sucursales/{id}	    Eliminar un producto por ID