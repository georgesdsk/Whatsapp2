# Whatsapp2

## Descripción

Aplicación de mensajería instantánea desarrollada en Java, inspirada en WhatsApp. Implementa el patrón de arquitectura MVC (Modelo-Vista-Controlador) y utiliza una base de datos SQL para la persistencia de datos.

## Características

- Sistema de autenticación de usuarios (login/registro)
- Mensajería instantánea entre usuarios
- Gestión de contactos y solicitudes de amistad
- Persistencia de datos mediante base de datos SQL
- Sistema de chats individuales
- Arquitectura MVC bien definida

## Arquitectura del Proyecto

El proyecto sigue el patrón MVC (Modelo-Vista-Controlador):

```
Whatsapp2/
├── baseDatos/          
│   └── YASTAAAAAAA.sql
├── entorno/            
│   ├── DIAGRAMA_CASOS.png
│   └── DIAGRAMA_CLASES.png
├── src/
│   ├── bbdd/           
│   │   └── PropiedadesConexion
│   ├── controlador/    
│   │   ├── GestoraBbdd.java
│   │   ├── Main.java
│   │   └── Messenger.java
│   ├── modelo/         
│   │   ├── Chat.java
│   │   ├── InicioSesion.java
│   │   ├── Mensaje.java
│   │   ├── Solicitud.java
│   │   └── Usuario.java
│   └── vista/          
│       └── Menu.java
└── sqljdbc_9.2/        
```

## Tecnologías

- Java SE
- SQL Server
- JDBC

## Capturas de Pantalla

### Diagrama de Casos de Uso

![Diagrama de Casos de Uso](entorno/DIAGRAMA_CASOS.png)

### Diagrama de Clases

![Diagrama de Clases](entorno/DIAGRAMA_CLASES.png)

## Estructura de la Base de Datos

### Tabla: usuarios
```sql
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL
);
```

### Tabla: chats
```sql
CREATE TABLE chats (
    id_chat INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario1 INT NOT NULL,
    id_usuario2 INT NOT NULL,
    FOREIGN KEY (id_usuario1) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_usuario2) REFERENCES usuarios(id_usuario)
);
```

### Tabla: mensajes
```sql
CREATE TABLE mensajes (
    id_mensaje INT PRIMARY KEY AUTO_INCREMENT,
    id_chat INT NOT NULL,
    id_remitente INT NOT NULL,
    contenido TEXT NOT NULL,
    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_chat) REFERENCES chats(id_chat),
    FOREIGN KEY (id_remitente) REFERENCES usuarios(id_usuario)
);
```

### Tabla: solicitudes
```sql
CREATE TABLE solicitudes (
    id_solicitud INT PRIMARY KEY AUTO_INCREMENT,
    id_remitente INT NOT NULL,
    id_destinatario INT NOT NULL,
    estado ENUM('pendiente', 'aceptada', 'rechazada') DEFAULT 'pendiente',
    FOREIGN KEY (id_remitente) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_destinatario) REFERENCES usuarios(id_usuario)
);
```
