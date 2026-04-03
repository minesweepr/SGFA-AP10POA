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
    faltou_aula BOOLEAN DEFAULT FALSE,
    professor_ausente BOOLEAN DEFAULT FALSE,
    nao_aplicavel BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (horario_dia_id) REFERENCES HorarioDia(id) ON DELETE CASCADE,
    FOREIGN KEY (disciplina_codigo) REFERENCES Disciplina(codigo)
);
