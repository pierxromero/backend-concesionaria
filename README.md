Concesionaria UTN

Concesionaria UTN es un sistema de software para la gestión de una concesionaria de vehículos que vende autos, motos y camiones. La aplicación centraliza y automatiza operaciones relacionadas con la administración de vehículos, personas (clientes y empleados) y ventas.

El sistema está diseñado para ser modular, escalable y fácil de mantener, con un enfoque en la integridad de los datos y la eficiencia operativa.

✨ Funcionalidades

Gestión de vehículos: Agregar, modificar, buscar y listar autos, motos y camiones.

Gestión de personas: Registrar, validar y administrar clientes y empleados.

Registro de ventas: Asociar vehículos con clientes y empleados, generando una factura digital. Permite el seguimiento y pago de planes de cuotas.

Validación de datos: Garantiza la consistencia de información como DNI y correo electrónico.

Persistencia de datos: Guarda y carga información en archivos JSON, asegurando que los datos se mantengan entre sesiones.

💻 Tecnologías

Java: Lenguaje principal del proyecto.

Programación Orientada a Objetos (OOP): Uso de herencia y polimorfismo para estructurar clases como Vehiculo y Personas.

Programación Genérica: La clase Gestor utiliza genéricos para manejar diferentes tipos de vehículos y personas, promoviendo reutilización y escalabilidad.

Colecciones: Se usan HashSet para vehículos y HashMap para personas, optimizando el manejo de datos.

Excepciones personalizadas: Control de errores y validaciones específicas a lo largo del sistema.

🛠️ Diseño del sistema

El diseño sigue un enfoque estructurado con fases de análisis, diseño, implementación, pruebas y documentación.

Clases principales

Vehiculo (abstracta): Base para Auto, Moto y Camion, maneja información genérica de vehículos y persistencia en JSON.

Personas (abstracta): Base para Empleado y Cliente, gestiona datos generales y serialización JSON.

Compra: Modela una transacción de venta, vinculando vehículo, cliente, empleado y factura generada.

Gestor: Clase genérica que gestiona operaciones (Agregar, Eliminar, Modificar) para vehículos y personas.

Concesionaria: Administra inventario, ventas y pagos interactuando con Gestor y Compra.

MenuSistema: Maneja la interfaz de usuario, inicio de sesión, carga de datos y navegación por menús.

Validaciones: Contiene métodos para validar información ingresada, como formato de DNI y correo electrónico.

Enumeraciones

Se utilizan enumeraciones para definir valores constantes y evitar errores de ingreso de datos, como:
TipoCombustible, TipoPago, TipoMoto, entre otros.

🚀 Cómo empezar

El sistema utiliza una interfaz de consola. Para usarlo:

Inicio de sesión

Al iniciar la aplicación, se solicitará ingresar DNI y contraseña.

Si el archivo Personas.json está vacío, se crearán automáticamente cuatro cuentas de empleado por defecto: Lautaro Ramos, Lautaro Castro, Maica Odera y Piero Visitación.

Menú principal

Tras iniciar sesión correctamente, se accede al menú principal con las siguientes opciones:

Gestión de vehículos: Agregar, eliminar, buscar, filtrar y modificar vehículos.

Gestión de personas: Registrar, eliminar, buscar y modificar clientes y empleados.

Gestión de ventas: Listar ventas, generar nuevas ventas y pagar cuotas.

Información de la concesionaria: Ver información general del negocio.

Salir: Cerrar la aplicación.

Explora los menús para realizar todas las operaciones de gestión disponibles.
TipoMoto

Permiten mantener consistencia y evitar errores de ingreso de datos.

