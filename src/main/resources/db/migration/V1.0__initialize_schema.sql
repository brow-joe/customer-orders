CREATE TABLE IF NOT EXISTS clientes (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	numero_controle VARCHAR(36) NOT NULL,
	data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valor NUMERIC(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    codigo_cliente BIGINT NOT NULL,
    desconto NUMERIC(10, 2) DEFAULT 0 NOT NULL,
    valor_total NUMERIC(10, 2) NOT NULL,
    CONSTRAINT pedidos_numero_controle_unique UNIQUE (numero_controle),
    CONSTRAINT pedidos_fk_cliente FOREIGN KEY (codigo_cliente) REFERENCES clientes(id)
);
