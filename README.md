🚗 Concesionaria UTN

Concesionaria UTN es un sistema de gestión de concesionarias de vehículos (autos, motos y camiones), que centraliza y automatiza operaciones de inventario, ventas y administración de personas (clientes y empleados).

El sistema es modular, escalable y fácil de mantener, con énfasis en integridad de datos y eficiencia operativa.

✨ Características principales

Gestión de vehículos: Agregar, eliminar, modificar, buscar y filtrar vehículos.

Gestión de personas: Registrar y administrar clientes y empleados con validaciones.

Registro de ventas: Asociar vehículos con clientes y empleados, generar facturas digitales y seguimiento de cuotas.

Validación de datos: Comprobación de DNI, correo electrónico y consistencia de datos.

Persistencia de información: Guardado y carga mediante JSON, asegurando que los datos se mantengan entre sesiones.

Excepciones personalizadas: Manejo de errores específicos y validaciones de negocio.

Interfaz de consola: Menús claros e interactivos para empleados y administradores.

🛠️ Tecnologías utilizadas

Java

Programación Orientada a Objetos (OOP): Herencia y polimorfismo.

Programación genérica: La clase Gestor maneja distintos tipos de vehículos y personas.

Colecciones de Java: HashSet para vehículos y HashMap para personas.

JSON: Persistencia de datos.

Excepciones personalizadas: Validaciones y control de errores.

🗂️ Estructura del proyecto
src/
├── main/
│   ├── java/
│   │   ├── entities/        # Clases de Vehículo, Personas y Compra
│   │   ├── services/        # Lógica de gestión y validaciones
│   │   ├── utils/           # Validaciones y helpers
│   │   └── MenuSistema.java # Interfaz de usuario
│   └── resources/
│       └── data/            # Archivos JSON de persistencia
└── test/                    # Pruebas unitarias y funcionales

🏗️ Clases principales

Vehiculo (abstracta): Base de Auto, Moto y Camion.

Personas (abstracta): Base de Empleado y Cliente.

Compra: Modela una venta y factura.

Gestor: Administración genérica de vehículos y personas.

Concesionaria: Coordina inventario, ventas y pagos.

MenuSistema: Controla la interfaz de consola.

Validaciones: Métodos de validación de DNI, email, etc.

🚀 Cómo usar

Clonar el repositorio:

git clone https://github.com/tu_usuario/concesionaria-utn.git
cd concesionaria-utn


Compilar el proyecto:

javac -d bin src/main/java/**/*.java


Ejecutar la aplicación:

java -cp bin MenuSistema


Iniciar sesión con DNI y contraseña. Si el archivo Personas.json está vacío, se crean cuentas de empleado por defecto: Lautaro Ramos, Lautaro Castro, Maica Odera y Piero Visitación.

Explorar el menú:

Gestión de vehículos

Gestión de personas

Gestión de ventas

Información de la concesionaria

📌 Enumeraciones importantes

TipoCombustible

TipoPago

TipoMoto

Permiten mantener consistencia y evitar errores de ingreso de datos.

