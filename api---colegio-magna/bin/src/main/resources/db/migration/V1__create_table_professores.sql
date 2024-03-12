CREATE TABLE professores (

    id BIGSERIAL PRIMARY KEY UNIQUE,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    idade VARCHAR(3) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    disciplina VARCHAR(40) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(40) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20),
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(50) NOT NULL

);