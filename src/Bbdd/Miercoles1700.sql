USE [Whatsapp2]
GO
/****** Object:  User [raul]    Script Date: 09/06/2021 16:57:47 ******/
CREATE USER [raul] FOR LOGIN [raul] WITH DEFAULT_SCHEMA=[raul]
GO
ALTER ROLE [db_owner] ADD MEMBER [raul]
GO
ALTER ROLE [db_accessadmin] ADD MEMBER [raul]
GO
ALTER ROLE [db_datareader] ADD MEMBER [raul]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [raul]
GO
/****** Object:  Schema [raul]    Script Date: 09/06/2021 16:57:47 ******/
CREATE SCHEMA [raul]
GO
/****** Object:  UserDefinedFunction [dbo].[FN_IDdelNombre]    Script Date: 09/06/2021 16:57:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   FUNCTION [dbo].[FN_IDdelNombre](@Login VarChar(30))
RETURNS SMALLINT
AS BEGIN RETURN( 
				SELECT ID FROM Usuario
				WHERE @Login = Login
					)
END
GO
/****** Object:  UserDefinedFunction [dbo].[FN_InicioSesion]    Script Date: 09/06/2021 16:57:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   FUNCTION [dbo].[FN_InicioSesion](@Login VarChar(30), @Password VarChar(20))
RETURNS SMALLINT
AS BEGIN RETURN( 
			SELECT ID FROM Usuario
				WHERE dbo.FN_IDdelNombre(@Login) = ID AND
				Contrasenia = @Password 
				
				)
END		
GO
/****** Object:  Table [dbo].[Chat]    Script Date: 09/06/2021 16:57:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Chat](
	[Nombre] [varchar](30) NOT NULL,
	[ID] [smallint] IDENTITY(1,1) NOT NULL,
	[FechaCreacion] [smalldatetime] NOT NULL,
 CONSTRAINT [PK_Chat] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChatUsuario]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChatUsuario](
	[IDUsuario] [smallint] NOT NULL,
	[IDChat] [smallint] NOT NULL,
 CONSTRAINT [PK_ChatUsuario] PRIMARY KEY CLUSTERED 
(
	[IDUsuario] ASC,
	[IDChat] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Mensaje]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mensaje](
	[IDUsuario] [smallint] NOT NULL,
	[Mensaje] [varchar](240) NOT NULL,
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[IDChat] [smallint] NOT NULL,
 CONSTRAINT [PK_Mensaje_1] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Solicitud]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Solicitud](
	[IDEmisor] [smallint] NOT NULL,
	[IDReceptor] [smallint] NOT NULL,
 CONSTRAINT [PK_Solicitud] PRIMARY KEY CLUSTERED 
(
	[IDEmisor] ASC,
	[IDReceptor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario](
	[ID] [smallint] NOT NULL,
	[Contrasenia] [varchar](20) NOT NULL,
	[Login] [varchar](30) NOT NULL,
 CONSTRAINT [PK_Usuarios] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UsuarioAmigo]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UsuarioAmigo](
	[IDEmisor] [smallint] NOT NULL,
	[IDReceptor] [smallint] NOT NULL,
 CONSTRAINT [PK_UsuarioAmigo] PRIMARY KEY CLUSTERED 
(
	[IDEmisor] ASC,
	[IDReceptor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChatUsuario]  WITH CHECK ADD  CONSTRAINT [FK_ChatUsuario] FOREIGN KEY([IDChat])
REFERENCES [dbo].[Chat] ([ID])
GO
ALTER TABLE [dbo].[ChatUsuario] CHECK CONSTRAINT [FK_ChatUsuario]
GO
ALTER TABLE [dbo].[ChatUsuario]  WITH CHECK ADD  CONSTRAINT [FK_UsuarioChat] FOREIGN KEY([IDUsuario])
REFERENCES [dbo].[Usuario] ([ID])
GO
ALTER TABLE [dbo].[ChatUsuario] CHECK CONSTRAINT [FK_UsuarioChat]
GO
ALTER TABLE [dbo].[Mensaje]  WITH CHECK ADD  CONSTRAINT [FK_Chat] FOREIGN KEY([IDChat])
REFERENCES [dbo].[Chat] ([ID])
GO
ALTER TABLE [dbo].[Mensaje] CHECK CONSTRAINT [FK_Chat]
GO
ALTER TABLE [dbo].[Mensaje]  WITH CHECK ADD  CONSTRAINT [FK_Usuario] FOREIGN KEY([IDUsuario])
REFERENCES [dbo].[Usuario] ([ID])
GO
ALTER TABLE [dbo].[Mensaje] CHECK CONSTRAINT [FK_Usuario]
GO
ALTER TABLE [dbo].[Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Emisor] FOREIGN KEY([IDEmisor])
REFERENCES [dbo].[Usuario] ([ID])
GO
ALTER TABLE [dbo].[Solicitud] CHECK CONSTRAINT [FK_Emisor]
GO
ALTER TABLE [dbo].[Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Receptor] FOREIGN KEY([IDReceptor])
REFERENCES [dbo].[Usuario] ([ID])
GO
ALTER TABLE [dbo].[Solicitud] CHECK CONSTRAINT [FK_Receptor]
GO
ALTER TABLE [dbo].[UsuarioAmigo]  WITH CHECK ADD  CONSTRAINT [FK_Usuario1] FOREIGN KEY([IDEmisor])
REFERENCES [dbo].[Usuario] ([ID])
GO
ALTER TABLE [dbo].[UsuarioAmigo] CHECK CONSTRAINT [FK_Usuario1]
GO
ALTER TABLE [dbo].[UsuarioAmigo]  WITH CHECK ADD  CONSTRAINT [FK_Usuario2] FOREIGN KEY([IDReceptor])
REFERENCES [dbo].[Usuario] ([ID])
GO
ALTER TABLE [dbo].[UsuarioAmigo] CHECK CONSTRAINT [FK_Usuario2]
GO
/****** Object:  StoredProcedure [dbo].[AgregarAmigo]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [dbo].[AgregarAmigo](@IDEmisor smallint, @IDReceptor smallint)
AS BEGIN
	INSERT INTO UsuarioAmigo VALUES (@IDEmisor, @IDReceptor)
END
GO
/****** Object:  StoredProcedure [dbo].[AnhadirPersonaAlChat]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[AnhadirPersonaAlChat](@IDUsuario smallint, @IDChat smallint)
AS BEGIN
	INSERT INTO ChatUsuario VALUES (@IDUsuario, @IDChat)
END
GO
/****** Object:  StoredProcedure [dbo].[DenegarSolicitud]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[DenegarSolicitud] (@IDEmisor smallint, @IDReceptor smallint)
AS BEGIN
	DELETE  FROM Solicitud WHERE (@IDReceptor = IDReceptor AND @IDEmisor = IDEmisor) OR (@IDReceptor =IDEmisor  AND @IDEmisor = IDReceptor)   -- NO LIARSE CON EMISOR Y RECEPTOR, DOS COMPROBACIONES POR SI SE HAN ENVIADO LOS DOS LA PETICION
END
GO
/****** Object:  StoredProcedure [dbo].[FN_EnviarPeticionAmistad]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   procedure [dbo].[FN_EnviarPeticionAmistad](@IDEmisor smallint, @LoginReceptor VarChar(30))
AS BEGIN 	
			DECLARE @IDReceptor  smallint = dbo.FN_IDdelNombre(@LoginReceptor)
			DECLARE @Resultado bit 
			set @Resultado = 0 --NO EXISTE USUARIO
			
		IF @IDEmisor IS NOT NULL AND @IDReceptor IS NOT NULL --SI  AMBOS NO SON NULOS
		BEGIN
			
			IF @IDEmisor != @IDReceptor				--  SI no son la misma persona
			BEGIN
			
				IF EXISTS (SELECT * FROM UsuarioAmigo							-- si ya esta en la tabla usuarioAmigo, tambien se podria con isNull
							WHERE (@IDReceptor = IDReceptor AND @IDEmisor = IDEmisor) 
							OR  (@IDReceptor =IDEmisor  AND @IDEmisor = IDReceptor))
				BEGIN
			
					SET @Resultado = 2 
				END
				ELSE BEGIN
						INSERT INTO Solicitud VALUES (@IDEmisor, @IDReceptor)
				END

			END
			ELSE BEGIN
				SET @Resultado = 3
			END

		END
		


		RETURN @Resultado
					
END
GO
/****** Object:  StoredProcedure [dbo].[PR_AceptarSolicitud]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [dbo].[PR_AceptarSolicitud](@IDEmisor smallint, @IDReceptor smallint)
AS BEGIN
	INSERT INTO UsuarioAmigo VALUES (@IDEmisor, @IDReceptor)
	DELETE  FROM Solicitud WHERE (@IDReceptor = IDReceptor AND @IDEmisor = IDEmisor) OR (@IDReceptor =IDEmisor  AND @IDEmisor = IDReceptor)   -- NO LIARSE CON EMISOR Y RECEPTOR, DOS COMPROBACIONES POR SI SE HAN ENVIADO LOS DOS LA PETICION
END

--8
GO
/****** Object:  StoredProcedure [dbo].[PR_AgregarSolicitud]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[PR_AgregarSolicitud](@IDEmisor smallint, @IDReceptor smallint)
AS BEGIN
	INSERT INTO Solicitud VALUES (@IDEmisor, @IDReceptor)
END
GO
/****** Object:  StoredProcedure [dbo].[PR_CrearNuevoChat]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[PR_CrearNuevoChat](@IDUsuario smallInt, @IDUsuarioInvitado smallInt, @Nombre varChar(30))
AS BEGIN

	DECLARE @FechaActual smallDateTime = CURRENT_TIMESTAMP
	DECLARE @IDChatCreado smallint
	
	INSERT INTO Chat(Nombre, FechaCreacion) values (@Nombre, @FechaActual)-- se puede hacer un trigger que salte depsues de anhadir un nuevo chat que cree un nuevo 
																		-- chatUsuario con el id del chat, pero se perderia el ID del USuario
	
	--FechaCreacion = @FechaActual AND Nombre = @Nombre)-- no se si aqui es con AS. Dudo que dos chats se creen con el mismo nombre en el mismo segundo

	INSERT INTO ChatUsuario values (@IDUsuario, @@IDENTITY)
	INSERT INTO ChatUsuario values (@IDUsuarioInvitado, @@IDENTITY)
	return @@IDENTITY

END
GO
/****** Object:  StoredProcedure [dbo].[RegistrarUsuario]    Script Date: 09/06/2021 16:57:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[RegistrarUsuario](@Login varchar(30), @Contrasenia varchar(20))
AS BEGIN
	INSERT INTO Usuario(Contrasenia, Login) values (@Contrasenia, @Login)
	END
GO