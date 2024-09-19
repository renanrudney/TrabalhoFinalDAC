DO
$$
BEGIN
    -- Create schemas
    CREATE SCHEMA IF NOT EXISTS Cliente;
    CREATE SCHEMA IF NOT EXISTS Funcionario;
    CREATE SCHEMA IF NOT EXISTS Voo;
    CREATE SCHEMA IF NOT EXISTS Reserva_cud;
    CREATE SCHEMA IF NOT EXISTS Reserva_read;

    -- Create tables

    /* MS Funcionario */
    CREATE TABLE IF NOT EXISTS Funcionario.Funcionario (
        id_usuario INT PRIMARY KEY,
        nome VARCHAR(50) NOT NULL,
        cpf VARCHAR(11) NOT NULL,
        email VARCHAR(30),
        telefone VARCHAR(9),
        ativo BOOL NOT NULL
    );

    /* MS Cliente */
    CREATE TABLE IF NOT EXISTS Cliente.Endereco (
        id SERIAL PRIMARY KEY,
        rua VARCHAR(30) NOT NULL,
        numero INT NOT NULL,
        complemento VARCHAR(30),
        cep VARCHAR(8) NOT NULL,
        cidade VARCHAR(30) NOT NULL,
        estado VARCHAR(2) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS Cliente.Cliente (
        id_usuario INT PRIMARY KEY,
        id_endereco INT NOT NULL,
        cpf VARCHAR(11) NOT NULL,
        nome VARCHAR(50) NOT NULL,
        email VARCHAR(30),
        milhas DOUBLE PRECISION DEFAULT 0,
        CONSTRAINT fk_cliente_endereco FOREIGN KEY (id_endereco) REFERENCES Cliente.Endereco(id)
    );

    CREATE TABLE IF NOT EXISTS Cliente.Transacao (
        id SERIAL PRIMARY KEY,
        id_cliente INT NOT NULL,
        data_hora TIMESTAMP NOT NULL,
        qtd_milhas DOUBLE PRECISION NOT NULL,
        entrada BOOL NOT NULL,
        descricao VARCHAR(100),
        CONSTRAINT fk_transacao_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente.Cliente(id_usuario)
    );

    /* MS Voos */
    CREATE TABLE IF NOT EXISTS Voo.Aeroporto (
        cod VARCHAR(3) PRIMARY KEY,
        nome VARCHAR(30) NOT NULL,
        cidade VARCHAR(30) NOT NULL,
        estado VARCHAR(2) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS Voo.Voo (
        cod VARCHAR(8) PRIMARY KEY,
        aeroporto_origem VARCHAR(3) NOT NULL,
        aeroporto_destino VARCHAR(3) NOT NULL,
        data DATE NOT NULL,
        valor_passagem DOUBLE PRECISION NOT NULL,
        qtd_poltronas_total INT NOT NULL,
        qtd_poltronas_ocupadas INT DEFAULT 0,
        CONSTRAINT fk_voo_origem FOREIGN KEY (aeroporto_origem) REFERENCES Voo.Aeroporto(cod),
        CONSTRAINT fk_voo_destino FOREIGN KEY (aeroporto_destino) REFERENCES Voo.Aeroporto(cod)
    );

    /* MS Reserva para CUD */
    CREATE TABLE IF NOT EXISTS Reserva_cud.Estado_reserva (
        cod SERIAL PRIMARY KEY,
        sigla VARCHAR(5) NOT NULL,
        descricao VARCHAR(50) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS Reserva_cud.Reserva (
        cod VARCHAR(6) PRIMARY KEY,
        cod_estado INT NOT NULL,
        cod_voo VARCHAR(8) NOT NULL,
        id_cliente INT NOT NULL,
        data_hora TIMESTAMP NOT NULL,
        CONSTRAINT fk_reserva_estado FOREIGN KEY (cod_estado) REFERENCES Reserva_cud.Estado_reserva(cod),
        CONSTRAINT fk_reserva_voo FOREIGN KEY (cod_voo) REFERENCES Voo.Voo(cod),
        CONSTRAINT fk_reserva_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente.Cliente(id_usuario)
    );

    CREATE TABLE IF NOT EXISTS Reserva_cud.Log_reserva (
        id SERIAL PRIMARY KEY,
        cod_reserva VARCHAR(6) NOT NULL,
        estado_origem INT NOT NULL,
        estado_destino INT NOT NULL,
        data_hora TIMESTAMP NOT NULL,
        CONSTRAINT fk_log_reserva_reserva FOREIGN KEY (cod_reserva) REFERENCES Reserva_cud.Reserva(cod),
        CONSTRAINT fk_log_reserva_estado_origem FOREIGN KEY (estado_origem) REFERENCES Reserva_cud.Estado_reserva(cod),
        CONSTRAINT fk_log_reserva_estado_destino FOREIGN KEY (estado_destino) REFERENCES Reserva_cud.Estado_reserva(cod)
    );

    /* MS Reserva para Read */
    CREATE TABLE IF NOT EXISTS Reserva_read.Estado_reserva (
        cod SERIAL PRIMARY KEY,
        sigla VARCHAR(5) NOT NULL,
        descricao VARCHAR(50) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS Reserva_read.Reserva (
        cod VARCHAR(6) PRIMARY KEY,
        cod_estado INT NOT NULL,
        cod_voo VARCHAR(8) NOT NULL,
        id_cliente INT NOT NULL,
        data_hora TIMESTAMP NOT NULL,
        CONSTRAINT fk_reserva_read_estado FOREIGN KEY (cod_estado) REFERENCES Reserva_read.Estado_reserva(cod),
        CONSTRAINT fk_reserva_read_voo FOREIGN KEY (cod_voo) REFERENCES Voo.Voo(cod),
        CONSTRAINT fk_reserva_read_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente.Cliente(id_usuario)
    );
END
$$;
