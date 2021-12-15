CREATE TABLE [posts] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [content] nvarchar(255),
  [description] nvarchar(255),
  [title] nvarchar(255)
)
GO

CREATE TABLE [comments] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [body] nvarchar(255),
  [email] nvarchar(255),
  [name] nvarchar(255),
  [postId] int
)
GO

CREATE TABLE [users] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [email] nvarchar(255),
  [name] nvarchar(255),
  [password] nvarchar(255),
  [username] nvarchar(255)
)
GO

CREATE TABLE [roles] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [name] nvarchar(255)
)
GO

CREATE TABLE [user_roles] (
  [user_id] int,
  [role_id] int,
  PRIMARY KEY ([user_id], [role_id])
)
GO

