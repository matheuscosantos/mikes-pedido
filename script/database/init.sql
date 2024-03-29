CREATE TABLE cliente(
    id VARCHAR(36) PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL
);

CREATE TABLE pedido(
    id VARCHAR(36) PRIMARY KEY,
    numero BIGINT NOT NULL,
    cliente_id VARCHAR(36),
    preco NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL
);

CREATE TABLE item_pedido(
    id VARCHAR(36) PRIMARY KEY,
    id_pedido VARCHAR(36) NOT NULL,
    nome_produto VARCHAR(255) NOT NULL,
    preco_produto NUMERIC(10, 2) NOT NULL,
    quantidade BIGINT NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,

    FOREIGN KEY (id_pedido) REFERENCES pedido (id)
);

CREATE TABLE produto(
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL
);

CREATE SEQUENCE numero_pedido START WITH 1 INCREMENT BY 1;