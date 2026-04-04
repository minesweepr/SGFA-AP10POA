-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 04, 2026 at 05:38 PM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `seligafalta`
--

-- --------------------------------------------------------

--
-- Table structure for table `aluno`
--

DROP TABLE IF EXISTS `aluno`;
CREATE TABLE IF NOT EXISTS `aluno` (
  `matricula` varchar(20) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`matricula`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `aluno`
--

INSERT INTO `aluno` (`matricula`, `nome`, `email`, `senha`) VALUES
('123456', 'Athena', 'athena@email.com', 'senha123');

-- --------------------------------------------------------

--
-- Table structure for table `auladisciplina`
--

DROP TABLE IF EXISTS `auladisciplina`;
CREATE TABLE IF NOT EXISTS `auladisciplina` (
  `id` int NOT NULL AUTO_INCREMENT,
  `horario_dia_id` int NOT NULL,
  `disciplina_codigo` varchar(20) NOT NULL,
  `quantidade_tempos` int NOT NULL DEFAULT '1',
  `faltou_aula` tinyint(1) DEFAULT '0',
  `professor_ausente` tinyint(1) DEFAULT '0',
  `nao_aplicavel` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `horario_dia_id` (`horario_dia_id`),
  KEY `disciplina_codigo` (`disciplina_codigo`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `auladisciplina`
--

INSERT INTO `auladisciplina` (`id`, `horario_dia_id`, `disciplina_codigo`, `quantidade_tempos`, `faltou_aula`, `professor_ausente`, `nao_aplicavel`) VALUES
(1, 101, '4SEG', 2, 0, 0, 0),
(2, 101, '4MOD', 2, 0, 0, 0),
(3, 102, '4EMP', 2, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
CREATE TABLE IF NOT EXISTS `disciplina` (
  `codigo` varchar(20) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `carga_horaria_total` int NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `disciplina`
--

INSERT INTO `disciplina` (`codigo`, `nome`, `carga_horaria_total`) VALUES
('4SEG', 'Segurança da Informação', 80),
('4MOD', 'Modelagem', 80),
('4EMP', 'Empreendedorismo', 40);

-- --------------------------------------------------------

--
-- Table structure for table `gradesemanal`
--

DROP TABLE IF EXISTS `gradesemanal`;
CREATE TABLE IF NOT EXISTS `gradesemanal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `semestre` varchar(20) NOT NULL,
  `aluno_matricula` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `aluno_matricula` (`aluno_matricula`)
) ENGINE=MyISAM AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `gradesemanal`
--

INSERT INTO `gradesemanal` (`id`, `semestre`, `aluno_matricula`) VALUES
(1, '2026.1', '123456'),
(100, '2026.1', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `horariodia`
--

DROP TABLE IF EXISTS `horariodia`;
CREATE TABLE IF NOT EXISTS `horariodia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grade_id` int NOT NULL,
  `dia_semana` varchar(20) NOT NULL,
  `faltou_dia_inteiro` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `grade_id` (`grade_id`)
) ENGINE=MyISAM AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `horariodia`
--

INSERT INTO `horariodia` (`id`, `grade_id`, `dia_semana`, `faltou_dia_inteiro`) VALUES
(101, 100, 'Segunda', 0),
(102, 100, 'Terça', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
