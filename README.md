### Descripción del Proyecto
Este proyecto es un sistema de gestión de pedidos desarrollado en Java con JavaFX para la interfaz gráfica y una base de datos H2 para el almacenamiento de datos. El sistema permite realizar operaciones básicas de un CRUD (Crear, Leer, Actualizar, Eliminar) sobre pedidos, clientes y detalles de pedidos.

## Funcionalidades Principales
**Gestión de Pedidos:**

**Crear nuevos pedidos.**

**Visualizar y filtrar pedidos existentes.**

Actualizar el estado de los pedidos.

Eliminar pedidos.

Gestión de Clientes:

Registrar nuevos clientes.

Visualizar y editar información de clientes.

Gestión de Detalles de Pedidos:

Agregar productos o servicios (conexiones) a un pedido.

Calcular automáticamente el monto total del pedido basado en los detalles.

## Base de Datos:

- Uso de la base de datos H2 para almacenar información de clientes, pedidos y detalles de pedidos.

Operaciones transaccionales para garantizar la integridad de los datos.

Interfaz Gráfica:

Interfaz intuitiva y responsive desarrollada con JavaFX.

Tablas interactivas para visualizar pedidos, clientes y detalles.

Tecnologías Utilizadas
Java: Lenguaje de programación principal.

JavaFX: Framework para la interfaz gráfica.

H2: Base de datos embebida para almacenamiento local.

JDBC: Conexión y manipulación de la base de datos.

Maven: Gestión de dependencias y construcción del proyecto.

### Estructura del Proyecto
##**Modelos:** Clases que representan las entidades del sistema (Pedidos, Clientes, DetallePedido, etc.).

#**Controladores:** Lógica de la interfaz gráfica y manejo de eventos.

**Utils:** Clases de utilidad para operaciones comunes (conexión a la base de datos, consultas SQL, etc.).

**Vistas:** Archivos FXML que definen la interfaz gráfica.

## Requisitos
**-Java JDK 11 o superior.**

**-Maven para la gestión de dependencias.**

**-H2 Database (incluida en el proyecto).**

# Cómo Ejecutar el Proyecto
Clona el repositorio.

Importa el proyecto en tu IDE favorito (Eclipse, IntelliJ, etc.).

Configura las dependencias de Maven.

Ejecuta la clase principal (Main.java) para iniciar la aplicación.
