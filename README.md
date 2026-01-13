# Distribuidora – Sistema de Gestión (Java Desktop)

Sistema de gestión comercial desarrollado en Java para una distribuidora, orientado a la administración integral de productos, clientes, empleados, ventas, gastos y reportes.  
El sistema funciona como aplicación de escritorio (Desktop) con base de datos SQL y generación de reportes en PDF.

Este proyecto fue desarrollado como primer proyecto completo y funcional, con enfoque en una PyME real.

---

## Características principales

El sistema permite gestionar:

- Productos (alta, baja, modificación, stock, precios)
- Clientes
- Empleados
- Usuarios y roles
- Ventas y facturación interna
- Gastos y fichas de egresos
- Devoluciones
- Sueldos
- Reportes en PDF
- Copias de seguridad de la base de datos

Incluye validaciones, control de datos y persistencia en base de datos.

---

## Tecnologías utilizadas

- Java (NetBeans)
- Java Swing (Interfaz gráfica)
- Base de datos SQL (local)
- JDBC
- Librería de generación de PDF
- Manejo de archivos para backups y restauración

---

## Estructura del proyecto
Distribuidora_Sistema/
├── src/
│ └── Clases/
│ ├── Products_window.java
│ ├── Clients_window.java
│ ├── Employee_window.java
│ ├── Sales_window.java
│ ├── Bills_window.java
│ ├── Returns_window.java
│ ├── Salary_window.java
│ ├── Add_modify_user.java
│ ├── PDF.java
│ ├── General_configurations.java
│ ├── File_Check_window.java
│ ├── Create_window.java
│ └── New_file_window.java
├── build/
└── nbproject/

Cada ventana representa un módulo funcional del sistema.

---

## Funcionalidades destacadas

### Gestión comercial
- Administración completa de productos, clientes y empleados
- Control de ventas y devoluciones
- Registro de gastos
- Cálculo de sueldos

### Seguridad
- Sistema de usuarios
- Manejo de permisos y roles

### Reportes
- Generación de reportes en PDF
- Listados y resúmenes de datos

### Backups
- Creación de copias de seguridad
- Restauración de datos
- Control de archivos y existencia de base de datos

---

## Requisitos

- Java JDK 8 o superior
- Sistema operativo Windows
- Base de datos SQL configurada (local)

---

## Ejecución

1. Abrir el proyecto en NetBeans
2. Configurar la conexión a la base de datos
3. Ejecutar la clase principal
4. Crear o cargar un archivo de base de datos desde el menú inicial

---

## Estado del proyecto

El sistema se encuentra aproximadamente al 85% de finalización.  
Las funcionalidades principales están implementadas y funcionando, pero pueden agregarse mejoras en:

- Interfaz visual
- Reportes avanzados
- Documentación interna
- Optimización de arquitectura

---

## Autor

Facundo  
Desarrollador Java

Este proyecto fue creado como primer sistema de gestión completo, con el objetivo de simular un entorno real de trabajo para una distribuidora.

El proyecto está organizado de la siguiente forma:

