CREATE TABLE Сотрудники (
  id_сотрудник INT PRIMARY KEY,
  имя VARCHAR(255),
  фамилия VARCHAR(255),
  почта VARCHAR(255),
  пароль VARCHAR(255),
  id_отдел INT,
  должность VARCHAR(255),
  FOREIGN KEY (id_отдел) REFERENCES Подразделения(id_отдел)
);

CREATE TABLE Подразделения (
  id_отдел INT PRIMARY KEY,
  название VARCHAR(255)
);

CREATE TABLE Вожатые (
  id_вожатый INT,
  id_сотрудник INT,
  id_мероприятие INT,
  PRIMARY KEY (id_вожатый, id_сотрудник),
  FOREIGN KEY (id_сотрудник) REFERENCES Сотрудники(id_сотрудник),
  FOREIGN KEY (id_мероприятие) REFERENCES Мероприятия(id_мероприятие)
);

CREATE TABLE Волонтёры (
  id_волонтёр INT,
  id_сотрудник INT,
  id_мероприятие INT,
  PRIMARY KEY (id_волонтёр, id_сотрудник),
  FOREIGN KEY (id_сотрудник) REFERENCES Сотрудники(id_сотрудник),
  FOREIGN KEY (id_мероприятие) REFERENCES Мероприятия(id_мероприятие)
);

CREATE TABLE Мероприятия (
  id_мероприятие INT PRIMARY KEY,
  название VARCHAR(255),
  id_вожатый INT,
  место VARCHAR(255),
  дата DATE,
  дополнительная_информация VARCHAR(255),
  FOREIGN KEY (id_вожатый) REFERENCES Сотрудники(id_сотрудник)
);
