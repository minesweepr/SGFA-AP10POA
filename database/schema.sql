CREATE DATABASE IF NOT EXISTS SeLigaFalta;
USE SeLigaFalta;

CREATE TABLE IF NOT EXISTS Aluno (
    matricula VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL 
);

CREATE TABLE IF NOT EXISTS Disciplina (
    codigo VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    carga_horaria_total INT NOT NULL
);

CREATE TABLE IF NOT EXISTS GradeSemanal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    semestre VARCHAR(20) NOT NULL,
    aluno_matricula VARCHAR(20) NOT NULL,
    FOREIGN KEY (aluno_matricula) REFERENCES Aluno(matricula) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS HorarioDia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    grade_id INT NOT NULL,
    dia_semana VARCHAR(20) NOT NULL, 
    faltou_dia_inteiro BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (grade_id) REFERENCES GradeSemanal(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS AulaDisciplina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    horario_dia_id INT NOT NULL,
    disciplina_codigo VARCHAR(20) NOT NULL,
    quantidade_tempos INT NOT NULL DEFAULT 1,
    tempo_inicio INT DEFAULT NULL,
    professor_ausente BOOLEAN DEFAULT FALSE,
    nao_aplicavel BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (horario_dia_id) REFERENCES HorarioDia(id) ON DELETE CASCADE,
    FOREIGN KEY (disciplina_codigo) REFERENCES Disciplina(codigo)
);

CREATE TABLE IF NOT EXISTS RegistroFalta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aula_disciplina_id INT NOT NULL,
    data_falta DATE NOT NULL,
    quantidade_tempos_perdidos INT NOT NULL,
    FOREIGN KEY (aula_disciplina_id) REFERENCES AulaDisciplina(id) ON DELETE CASCADE
);

INSERT INTO Aluno (matricula, nome, email, senha) VALUES
('123456', 'Athena', 'athena@email.com', 'senha123');

INSERT INTO GradeSemanal (id, semestre, aluno_matricula) VALUES
(100, '2026.1', '123456');

INSERT INTO HorarioDia (id, grade_id, dia_semana, faltou_dia_inteiro) VALUES
(101, 100, 'Segunda', 0),
(102, 100, 'Terça', 0),
(103, 100, 'Quarta', 0),
(104, 100, 'Quinta', 0),
(105, 100, 'Sexta', 0);

INSERT INTO Disciplina (codigo, nome, carga_horaria_total) VALUES
('1FAC', 'Fundamentos de Algoritmos de Computação', 80),
('1IAS', 'Introdução à Análise de Sistemas', 80),
('1IHM', 'Interface Homem-Máquina', 40),
('1LPO', 'Língua Portuguesa', 80),
('1MAB', 'Matemática Básica', 80),
('1MAC', 'Matemática para Computação', 80),
('1ORG', 'Organização de Computadores', 80),
('2CAL', 'Cálculo', 80),
('2CAW', 'Construção de Aplicações WEB', 80),
('2FPR', 'Fundamentos de Programação', 80),
('2LES', 'Língua Estrangeira', 40),
('2MPA', 'Métodos e Processos Administrativos', 40),
('2REQ', 'Engenharia de Requisitos', 80),
('2SOP', 'Fundamentos de Sistemas Operacionais', 80),
('2TPH', 'Técnicas e Paradigmas Humanos', 80),
('3ALG', 'Álgebra', 80),
('3DAW', 'Desenvolvimento de Tecnologias WEB', 80),
('3ESD', 'Estrutura de Dados', 80),
('3PBD', 'Projeto de Banco de Dados', 80),
('3POB', 'Programação Orientada a Objetos Básica', 80),
('3RSD', 'Fundamentos de Redes e Sistemas Distribuídos', 80),
('4ADS', 'Tópicos em ADS', 80),
('4EMP', 'Empreendedorismo e Inovação', 40),
('4EST', 'Estatística e Probabilidade', 80),
('4MET', 'Metodologia da Pesquisa', 40),
('4MOD', 'Modelagem de Sistemas', 80),
('4POA', 'Programação Orientada a Objetos Avançada', 80),
('4SEG', 'Segurança da Informação', 80),
('4UBD', 'Utilização de Banco de Dados e SQL', 80),
('5GPS', 'Gerência e Projeto de Sistemas', 40),
('5PDM', 'Programação de Dispositivos Móveis', 80),
('5PJS', 'Projeto de Sistemas', 80),
('5SBD', 'Programação de Scripts de Banco de Dados', 80),
('5TAV', 'Tópicos Avançados', 80);

INSERT INTO AulaDisciplina (id, horario_dia_id, disciplina_codigo, quantidade_tempos, tempo_inicio, professor_ausente, nao_aplicavel) VALUES
(1, 101, '4SEG', 2, 1, 0, 0),
(2, 101, '4MOD', 2, 3, 0, 0),
(3, 102, '4EMP', 2, 1, 0, 0);

INSERT INTO RegistroFalta (aula_disciplina_id, data_falta, quantidade_tempos_perdidos) VALUES
(1, '2026-04-01', 2),
(1, '2026-04-03', 2),
(2, '2026-04-01', 2);