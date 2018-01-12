create database BarbeariaClub_BD;

use BarbeariaClub_BD;

CREATE TABLE Cliente(
	email varchar(50) not null,
    nome varchar(50) not null,
    ddd int (2) not null,
    telefone varchar(9) not null,
    senha varchar(15) not null,
    PRIMARY KEY (email)
);

INSERT INTO Cliente VALUES('pedrobrito17@gmail.com','Pedro Ítalo Aragão Brito', 98, '982045453', 'fsadu1788');

CREATE TABLE Funcionario (
	nome_funcionario varchar(50) NOT NULL,
	email_funcionario VARCHAR(50) NOT NULL,
	senha VARCHAR(15) NOT NULL,
    PRIMARY KEY(email_funcionario)
);

CREATE TABLE Servico (
	desc_servico VARCHAR(30) NOT NULL,
	valor FLOAT NOT NULL,
    PRIMARY KEY(desc_servico)
);

CREATE TABLE Atendimento (
	id_atendimento INT(4) auto_increment,
	email_cliente VARCHAR(50) NOT NULL,
	email_func VARCHAR(50) NOT NULL,
	desc_serv VARCHAR(30) NOT NULL,
	data_atendimento DATE NOT NULL,
	hora_atendimento TIME NOT NULL,
    PRIMARY KEY(id_atendimento),
    FOREIGN KEY(email_cliente) REFERENCES Cliente(email),
    FOREIGN KEY(email_func) REFERENCES Funcionario(email_funcionario),
    FOREIGN KEY(desc_serv) REFERENCES Servico(desc_servico)    
);

INSERT INTO Servico VALUES ('Corte de cabelo com tesoura', 18.00);
INSERT INTO Servico VALUES ('Corte de cabelo com máquina', 16.00);
INSERT INTO Servico VALUES ('Barba', 12.00);

INSERT INTO Funcionario VALUES('José Henrique Barbosa','henrique123@gmail.com','henrique456');
INSERT INTO Funcionario VALUES('Rafael Silva Menezes','rafa_mene@gmail.com','rafael456');
INSERT INTO Funcionario VALUES('Fernando Raposo Bezerra','fernando_raposo@gmail.com','fernando456');

INSERT INTO Atendimento(email_cliente,email_func,desc_serv,data_atendimento,hora_atendimento) 
 VALUES ('pedrobrito17@gmail.com','fernando_raposo@gmail.com','Corte de cabelo com tesoura','2016-08-15','08:30:00');


select count(email_cliente) from Atendimento where email_cliente='pedrobrito17@gmail.com';

select * from Atendimento where data_atendimento>=curdate() order by data_atendimento asc;

select * from Cliente;


