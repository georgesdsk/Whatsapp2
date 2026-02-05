# Whatsapp2 💬

## Descripción

Aplicación de mensajería instantánea desarrollada en Java con interfaz gráfica Swing, inspirada en WhatsApp. Implementa el patrón de arquitectura MVC (Modelo-Vista-Controlador) y utiliza una base de datos SQL para la persistencia de datos.

## ✨ Características

- 🔐 Sistema de autenticación de usuarios (login/registro)
- 💬 Mensajería instantánea entre usuarios
- 👥 Gestión de contactos y solicitudes de amistad
- 💾 Persistencia de datos mediante base de datos SQL
- 🎨 Interfaz gráfica intuitiva con Java Swing
- 📊 Sistema de chats individuales
- ⚡ Arquitectura MVC bien definida

## 🏗️ Arquitectura del Proyecto

El proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)**:

```
Whatsapp2/
├── baseDatos/          # Scripts SQL para la base de datos
│   └── YASTAAAAAAA.sql
├── entorno/            # Diagramas de casos de uso y clases
│   ├── DIAGRAMA_CASOS.png
│   └── DIAGRAMA_CLASES.png
├── src/
│   ├── bbdd/           # Configuración de conexión a BD
│   │   └── PropiedadesConexion
│   ├── controlador/    # Lógica de control de la aplicación
│   │   ├── GestoraBbdd.java
│   │   ├── Main.java
│   │   └── Messenger.java
│   ├── modelo/         # Clases del modelo de datos
│   │   ├── Chat.java
│   │   ├── InicioSesion.java
│   │   ├── Mensaje.java
│   │   ├── Solicitud.java
│   │   └── Usuario.java
│   └── vista/          # Interfaz gráfica
│       └── Menu.java
└── sqljdbc_9.2/        # Driver JDBC para SQL Server
```

## 🛠️ Tecnologías Utilizadas

- **Java SE** - Lenguaje de programación principal
- **Java Swing** - Framework para la interfaz gráfica
- **SQL Server** - Sistema de gestión de base de datos
- **JDBC** - Conector Java para bases de datos SQL
- **IntelliJ IDEA / NetBeans** - IDE de desarrollo

## 📋 Requisitos Previos

- **Java JDK 8** o superior
- **SQL Server** (o compatible)
- **JDBC Driver** para SQL Server (incluido en `sqljdbc_9.2/`)
- IDE Java (IntelliJ IDEA, Eclipse, NetBeans)

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/georgesdsk/Whatsapp2.git
cd Whatsapp2
```

### 2. Configurar la Base de Datos

1. Instalar SQL Server o utilizar una instancia existente
2. Ejecutar el script SQL ubicado en `baseDatos/YASTAAAAAAA.sql`

```sql
-- Desde SQL Server Management Studio o tu cliente SQL preferido
source baseDatos/YASTAAAAAAA.sql
```

3. Configurar las credenciales de conexión en `src/bbdd/PropiedadesConexion`:

```properties
url=jdbc:sqlserver://localhost:1433;databaseName=whatsapp2
username=tu_usuario
password=tu_contraseña
```

### 3. Compilar y Ejecutar

**Desde línea de comandos:**

```bash
# Compilar
javac -d bin -cp "sqljdbc_9.2/*" src/**/*.java

# Ejecutar
java -cp "bin:sqljdbc_9.2/*" controlador.Main
```

**Desde un IDE:**

1. Abrir el proyecto en tu IDE favorito
2. Añadir el driver JDBC a las librerías del proyecto (`sqljdbc_9.2/`)
3. Ejecutar la clase `controlador.Main.java`

## 📖 Uso

### Registro de Usuario

1. Al iniciar la aplicación, selecciona "Registrarse"
2. Completa los datos del formulario (nombre, usuario, contraseña)
3. Confirma el registro

### Inicio de Sesión

1. Introduce tu usuario y contraseña
2. Haz clic en "Iniciar Sesión"

### Enviar Mensajes

1. Selecciona un contacto de tu lista
2. Escribe tu mensaje en el campo de texto
3. Presiona "Enviar" o Enter

### Agregar Contactos

1. Ve a la sección de "Añadir contacto"
2. Busca el usuario por nombre
3. Envía una solicitud de amistad
4. Espera a que el usuario acepte la solicitud

## 🗄️ Modelo de Datos

El sistema utiliza las siguientes entidades principales:

- **Usuario**: Información de los usuarios (id, nombre, usuario, contraseña)
- **Chat**: Conversaciones entre usuarios
- **Mensaje**: Contenido de los mensajes (texto, fecha, remitente, destinatario)
- **Solicitud**: Peticiones de amistad pendientes
- **InicioSesion**: Gestión de sesiones activas

## 📊 Diagramas

En la carpeta `entorno/` puedes encontrar:

- **Diagrama de Casos de Uso**: Muestra las funcionalidades disponibles
- **Diagrama de Clases**: Estructura de las clases del sistema

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Si deseas mejorar el proyecto:

1. Haz un fork del repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📝 To-Do / Mejoras Futuras

- [ ] Implementar mensajería en tiempo real con sockets
- [ ] Añadir grupos de chat
- [ ] Soporte para envío de archivos e imágenes
- [ ] Notificaciones push
- [ ] Cifrado end-to-end de mensajes
- [ ] Migrar a base de datos más moderna (PostgreSQL/MySQL)
- [ ] Refactorizar a arquitectura cliente-servidor
- [ ] Interfaz responsive con JavaFX o migración a web

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👤 Autor

**George**
- GitHub: [@georgesdsk](https://github.com/georgesdsk)

---

⭐ Si te ha gustado este proyecto, ¡dale una estrella en GitHub!
