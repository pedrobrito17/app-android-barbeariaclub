create database bd_escola;

use bd_escola;

CREATE TABLE `aluno` (
  `matricula` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `nome_mae` varchar(255) DEFAULT NULL,
  `nome_pai` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `turma` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`matricula`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into aluno (matricula,nome,nome_mae,nome_pai,telefone,turma) values
(1,"Ana Paula Ribeiro Silva","Silvana Ribeiro Silva","Ricardo Ribeiro Silva","988586066","2A"),
(2,"Pedro Henrique Ramos Andrade","Helena Ramos Andrade","Paulo Pontes Andrade", "98653566", "2A"),
(3,"Juliana Almeida Pereira","Maria de Jesus Almeida Pereira", "Joao Penha Pereira", "988563522", "2A"),
(4,"Davi Caldas Brito", "Luciene Caldas Brito", "Marcilio Aragao Brito", "9885477863", "2A"),
(5,"Laura Viana Almeida", "Thais Viana Almeida", "Pedro Selsaf Brito", "985224565", "2A");

insert into aluno (matricula,nome,nome_mae,nome_pai,telefone,turma) values
(6,"Ricardo Ribeiro Silva","Ana Paula Ribeiro Silva","Silvano Ribeiro Silva","988586066","3B"),
(7,"Paulo Pontes Andrade","Helena Ramos Andrade","Pedro Henrique Ramos Andrade", "98653566", "3B"),
(8,"Joao Penha Pereira","Juliana Almeida Pereira","Henrique Pereira","988563522", "3B"),
(9,"Marcilio Aragao Brito", "Luciene Caldas Brito","Davi Caldas Brito","9885477863", "3B"),
(10,"Thais Viana Almeida","Laura Viana Almeida","Pedro Selsaf Brito", "985224565", "3B");

