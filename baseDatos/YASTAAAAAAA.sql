USE [Whatsapp2]
GO
/****** Object:  User [holabuenas]    Script Date: 15/06/2021 14:37:34 ******/
CREATE USER [holabuenas] FOR LOGIN [holabuenas] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [raul]    Script Date: 15/06/2021 14:37:34 ******/
CREATE USER [raul] FOR LOGIN [raul] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [raul2]    Script Date: 15/06/2021 14:37:34 ******/
CREATE USER [raul2] FOR LOGIN [raul2] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  UserDefinedFunction [dbo].[FN_IDdelNombre]    Script Date: 15/06/2021 14:37:34 ******/
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
/****** Object:  UserDefinedFunction [dbo].[FN_InicioSesion]    Script Date: 15/06/2021 14:37:34 ******/
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
/****** Object:  UserDefinedFunction [dbo].[FN_MensajesPendientes]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   FUNCTION [dbo].[FN_MensajesPendientes](@idUsuario smallint, @IDChat smallint)
RETURNS INT AS BEGIN

	Declare @NumMensajes as int
	set @NumMensajes =(
		select count(M.ID) from Mensaje as M
		INNER JOIN ChatUsuario AS CU
		ON CU.IDChat = M.IDChat
		WHERE CU.IDUsuario = @idUsuario --AND M.ID CH.UltimoMensaje
	)
	return @numMensajes

end
GO
/****** Object:  Table [dbo].[UsuarioAmigo]    Script Date: 15/06/2021 14:37:34 ******/
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
/****** Object:  Table [dbo].[Usuario]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario](
	[ID] [smallint] IDENTITY(1,1) NOT NULL,
	[Contrasenia] [varchar](20) NOT NULL,
	[Login] [varchar](30) NOT NULL,
 CONSTRAINT [PK_Usuarios] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[FN_Amigos]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   FUNCTION [dbo].[FN_Amigos] (@IDUsuario smallint)
RETURNS TABLE AS RETURN(
						SELECT U.ID , U.Login FROM Usuario AS U
                        INNER JOIN (SELECT IDEmisor AS ID from UsuarioAmigo
						where IDReceptor = @IDUsuario
						UNION SELECT IDReceptor AS ID from UsuarioAmigo
						where IDEmisor = @IDUsuario--@IDUsuario
						)  AS A ON A.ID = U.ID)

GO
/****** Object:  Table [dbo].[Chat]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Chat](
	[Nombre] [varchar](30) NOT NULL,
	[ID] [smallint] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_Chat] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChatUsuario]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChatUsuario](
	[IDUsuario] [smallint] NOT NULL,
	[IDChat] [smallint] NOT NULL,
	[IDUltimoMensaje] [int] NULL,
 CONSTRAINT [PK_ChatUsuario] PRIMARY KEY CLUSTERED 
(
	[IDUsuario] ASC,
	[IDChat] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Mensaje]    Script Date: 15/06/2021 14:37:34 ******/
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
/****** Object:  Table [dbo].[Solicitud]    Script Date: 15/06/2021 14:37:34 ******/
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
SET IDENTITY_INSERT [dbo].[Chat] ON 
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'Chat1', 1)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'Chat2', 2)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'Chat2', 3)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'Chat2', 4)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'Chat3', 5)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'true', 6)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'hombreee', 7)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'unico', 8)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'Chatillo', 9)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'2ç', 10)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'Chatillo2', 11)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'''asdfas''', 19)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'''HOmbreee''', 20)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'''FOL''', 21)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'''FOL''', 22)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'''si''', 23)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'''hola''', 24)
GO
INSERT [dbo].[Chat] ([Nombre], [ID]) VALUES (N'''1111111''', 25)
GO
SET IDENTITY_INSERT [dbo].[Chat] OFF
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (1, 1, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (1, 2, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (1, 3, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (1, 4, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (1, 5, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (1, 20, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 1, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 2, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 3, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 4, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 5, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 10, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 20, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 22, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 24, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (2, 25, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 6, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 7, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 8, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 9, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 10, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 11, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 19, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 21, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 22, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 23, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 24, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (102, 25, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (104, 9, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (104, 11, NULL)
GO
INSERT [dbo].[ChatUsuario] ([IDUsuario], [IDChat], [IDUltimoMensaje]) VALUES (104, 19, NULL)
GO
SET IDENTITY_INSERT [dbo].[Mensaje] ON 
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'asdfasfads', 1, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'adsfadsfadfdsa', 2, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'asdfadsffasdafdsfads', 3, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'asdfdsfasdffad', 4, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'f', 5, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'F', 6, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'dsfg', 7, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'sdfgdfsg', 8, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'f', 9, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'asdfasdf', 10, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'fasdfasdfas', 11, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'f', 12, 1)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (102, N'asdfdsads', 13, 19)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (102, N'asdfdsffasd', 14, 19)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'hola que tal ', 15, 20)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'no ha salido los mensajes ', 16, 20)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (1, N'que cono', 17, 20)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (102, N'asdfas', 18, 24)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (102, N'asdfhoasdhf', 19, 25)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (102, N'dsfafasdf', 20, 25)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (102, N'asdfafdasf', 21, 25)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (102, N'hoal que tal', 22, 6)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (102, N'muy bien y tu', 23, 6)
GO
INSERT [dbo].[Mensaje] ([IDUsuario], [Mensaje], [ID], [IDChat]) VALUES (104, N'asdfdfas', 24, 9)
GO
SET IDENTITY_INSERT [dbo].[Mensaje] OFF
GO
INSERT [dbo].[Solicitud] ([IDEmisor], [IDReceptor]) VALUES (104, 5)
GO
SET IDENTITY_INSERT [dbo].[Usuario] ON 
GO
INSERT [dbo].[Usuario] ([ID], [Contrasenia], [Login]) VALUES (1, N'hola2', N'hola')
GO
INSERT [dbo].[Usuario] ([ID], [Contrasenia], [Login]) VALUES (2, N'usuario', N'nuevo')
GO
INSERT [dbo].[Usuario] ([ID], [Contrasenia], [Login]) VALUES (5, N'hola2', N'hola3')
GO
INSERT [dbo].[Usuario] ([ID], [Contrasenia], [Login]) VALUES (102, N'raul', N'raul')
GO
INSERT [dbo].[Usuario] ([ID], [Contrasenia], [Login]) VALUES (103, N'hola', N'hola')
GO
INSERT [dbo].[Usuario] ([ID], [Contrasenia], [Login]) VALUES (104, N'1', N'1')
GO
INSERT [dbo].[Usuario] ([ID], [Contrasenia], [Login]) VALUES (105, N'2', N'2')
GO
INSERT [dbo].[Usuario] ([ID], [Contrasenia], [Login]) VALUES (106, N'raul3', N'raul3')
GO
SET IDENTITY_INSERT [dbo].[Usuario] OFF
GO
INSERT [dbo].[UsuarioAmigo] ([IDEmisor], [IDReceptor]) VALUES (1, 102)
GO
INSERT [dbo].[UsuarioAmigo] ([IDEmisor], [IDReceptor]) VALUES (1, 104)
GO
INSERT [dbo].[UsuarioAmigo] ([IDEmisor], [IDReceptor]) VALUES (2, 104)
GO
ALTER TABLE [dbo].[ChatUsuario]  WITH CHECK ADD  CONSTRAINT [FK_ChatUsuario] FOREIGN KEY([IDChat])
REFERENCES [dbo].[Chat] ([ID])
GO
ALTER TABLE [dbo].[ChatUsuario] CHECK CONSTRAINT [FK_ChatUsuario]
GO
ALTER TABLE [dbo].[ChatUsuario]  WITH CHECK ADD  CONSTRAINT [FK_UltimoMensaje] FOREIGN KEY([IDUltimoMensaje])
REFERENCES [dbo].[Mensaje] ([ID])
GO
ALTER TABLE [dbo].[ChatUsuario] CHECK CONSTRAINT [FK_UltimoMensaje]
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
/****** Object:  StoredProcedure [dbo].[AgregarAmigo]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE     PROCEDURE [dbo].[AgregarAmigo](@IDEmisor smallint, @IDReceptor smallint)
AS BEGIN
	INSERT INTO UsuarioAmigo VALUES (@IDEmisor, @IDReceptor)
END
GO
/****** Object:  StoredProcedure [dbo].[AnhadirPersonaAlChat]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE     PROCEDURE [dbo].[AnhadirPersonaAlChat](@IDUsuario smallint, @IDChat smallint)
AS BEGIN
	INSERT INTO ChatUsuario VALUES (@IDUsuario, @IDChat)
END
GO
/****** Object:  StoredProcedure [dbo].[DenegarSolicitud]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[DenegarSolicitud] (@IDEmisor smallint, @IDReceptor smallint)
AS BEGIN
	DELETE  FROM Solicitud WHERE (@IDReceptor = IDReceptor AND @IDEmisor = IDEmisor) OR (@IDReceptor =IDEmisor  AND @IDEmisor = IDReceptor)   -- NO LIARSE CON EMISOR Y RECEPTOR, DOS COMPROBACIONES POR SI SE HAN ENVIADO LOS DOS LA PETICION
END
GO
/****** Object:  StoredProcedure [dbo].[FN_EnviarPeticionAmistad]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE     procedure [dbo].[FN_EnviarPeticionAmistad](@IDEmisor smallint, @LoginReceptor VarChar(30))
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
/****** Object:  StoredProcedure [dbo].[PR_AceptarSolicitud]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE     PROCEDURE [dbo].[PR_AceptarSolicitud](@IDEmisor smallint, @IDReceptor smallint)
AS BEGIN
	INSERT INTO UsuarioAmigo VALUES (@IDEmisor, @IDReceptor)
	DELETE  FROM Solicitud WHERE (@IDReceptor = IDReceptor AND @IDEmisor = IDEmisor) OR (@IDReceptor =IDEmisor  AND @IDEmisor = IDReceptor)   -- NO LIARSE CON EMISOR Y RECEPTOR, DOS COMPROBACIONES POR SI SE HAN ENVIADO LOS DOS LA PETICION
END
GO
/****** Object:  StoredProcedure [dbo].[PR_CrearNuevoChat]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE     PROCEDURE [dbo].[PR_CrearNuevoChat](@IDUsuario smallInt, @IDUsuarioInvitado smallInt, @Nombre varChar(30), @ID smallint OUTPUT)
AS BEGIN

	
	DECLARE @IDChatCreado smallint
	

	INSERT INTO Chat(Nombre) values (@Nombre)-- se puede hacer un trigger que salte depsues de anhadir un nuevo chat que cree un nuevo 
	SET @ID =  @@identity														-- chatUsuario con el id del chat, pero se perderia el ID del USuario
	
	--FechaCreacion = @FechaActual AND Nombre = @Nombre)-- no se si aqui es con AS. Dudo que dos chats se creen con el mismo nombre en el mismo segundo

	INSERT INTO ChatUsuario(IDUsuario, IDChat) values (@IDUsuario, @ID)
	INSERT INTO ChatUsuario(IDUsuario, IDChat) values (@IDUsuarioInvitado, @ID)
	--RETURN 

END
GO
/****** Object:  StoredProcedure [dbo].[PR_DenegarSolicitud]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[PR_DenegarSolicitud] (@IDEmisor smallint, @IDReceptor smallint)
AS BEGIN
	DELETE  FROM Solicitud WHERE (@IDReceptor = IDReceptor AND @IDEmisor = IDEmisor) OR (@IDReceptor =IDEmisor  AND @IDEmisor = IDReceptor)   -- NO LIARSE CON EMISOR Y RECEPTOR, DOS COMPROBACIONES POR SI SE HAN ENVIADO LOS DOS LA PETICION
END
GO
/****** Object:  StoredProcedure [dbo].[PR_EnviarMensaje]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[PR_EnviarMensaje] @mensaje varChar(240), @idUSuario smallInt, @idChat smallint
AS BEGIN
	INSERT INTO Mensaje(Mensaje, IDUsuario, IDChat) VALUES (@mensaje,@idUSuario, @idChat)
END
GO
/****** Object:  StoredProcedure [dbo].[RegistrarUsuario]    Script Date: 15/06/2021 14:37:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE     PROCEDURE [dbo].[RegistrarUsuario](@Login varchar(30), @Contrasenia varchar(20))
AS BEGIN
	INSERT INTO Usuario(Contrasenia, Login) values (@Contrasenia, @Login)
	END
GO
