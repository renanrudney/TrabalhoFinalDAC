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
        id_funcionario UUID PRIMARY KEY,
        nome VARCHAR(50) NOT NULL,
        cpf VARCHAR(11) NOT NULL,
        email VARCHAR(50),
        telefone VARCHAR(11),
        ativo BOOL NOT NULL
    );

    /* MS Cliente */
    CREATE TABLE IF NOT EXISTS Cliente.Endereco (
        id_endereco UUID PRIMARY KEY,
        rua VARCHAR(50) NOT NULL,
        numero INT NOT NULL,
        complemento VARCHAR(30),
        cep VARCHAR(8) NOT NULL,
        cidade VARCHAR(30) NOT NULL,
        estado VARCHAR(2) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS Cliente.Cliente (
        id UUID PRIMARY KEY,
        id_endereco UUID NOT NULL,
        cpf VARCHAR(11) NOT NULL,
        nome VARCHAR(50) NOT NULL,
        email VARCHAR(50),
        milhas DOUBLE PRECISION DEFAULT 0,
        ativo BOOL NOT NULL,
        CONSTRAINT fk_cliente_endereco FOREIGN KEY (id_endereco) REFERENCES Cliente.Endereco(id_endereco)
    );

    CREATE TABLE IF NOT EXISTS Cliente.Transacao (
        id SERIAL PRIMARY KEY,
        id_cliente UUID NOT NULL,
        data_hora TIMESTAMP NOT NULL,
        qtd_milhas DOUBLE PRECISION NOT NULL,
        entrada BOOL NOT NULL,
        descricao VARCHAR(100),
        CONSTRAINT fk_transacao_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente.Cliente(id)
    );

    /* MS Voos */
    CREATE TABLE IF NOT EXISTS Voo.Aeroporto (
        cod VARCHAR(3) PRIMARY KEY,
        nome VARCHAR(50) NOT NULL,
        cidade VARCHAR(30) NOT NULL,
        estado VARCHAR(2) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS Voo.Voo (
        cod VARCHAR(8) PRIMARY KEY,
        aeroporto_origem VARCHAR(3) NOT NULL,
        aeroporto_destino VARCHAR(3) NOT NULL,
        data DATE NOT NULL,
        valor_passagem DOUBLE PRECISION NOT NULL,
        cod_estado INT NOT NULL,
        qtd_poltronas_total INT NOT NULL,
        qtd_poltronas_ocupadas INT DEFAULT 0,
        CONSTRAINT fk_voo_origem FOREIGN KEY (aeroporto_origem) REFERENCES Voo.Aeroporto(cod),
        CONSTRAINT fk_voo_destino FOREIGN KEY (aeroporto_destino) REFERENCES Voo.Aeroporto(cod)
    );

    /* MS Reserva para CUD */
    CREATE TABLE IF NOT EXISTS Reserva_cud.Estado_reserva (
        sigla VARCHAR(5) PRIMARY KEY,
        descricao VARCHAR(50) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS Reserva_cud.Reserva (
        cod VARCHAR(6) PRIMARY KEY,
        cod_estado VARCHAR(5) NOT NULL,
        cod_voo VARCHAR(8) NOT NULL,
        id_cliente UUID NOT NULL,
        data_hora TIMESTAMP NOT NULL,
        milhas DOUBLE PRECISION DEFAULT 0,
        valor DOUBLE PRECISION DEFAULT 0,
        CONSTRAINT fk_reserva_estado FOREIGN KEY (cod_estado) REFERENCES Reserva_cud.Estado_reserva(sigla),
        CONSTRAINT fk_reserva_voo FOREIGN KEY (cod_voo) REFERENCES Voo.Voo(cod),
        CONSTRAINT fk_reserva_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente.Cliente(id)
    );

    CREATE TABLE IF NOT EXISTS Reserva_cud.Log_reserva (
        id SERIAL PRIMARY KEY,
        cod_reserva VARCHAR(6) NOT NULL,
        estado_origem VARCHAR(5) NOT NULL,
        estado_destino VARCHAR(5) NOT NULL,
        data_hora TIMESTAMP NOT NULL,
        CONSTRAINT fk_log_reserva_reserva FOREIGN KEY (cod_reserva) REFERENCES Reserva_cud.Reserva(cod),
        CONSTRAINT fk_log_reserva_estado_origem FOREIGN KEY (estado_origem) REFERENCES Reserva_cud.Estado_reserva(sigla),
        CONSTRAINT fk_log_reserva_estado_destino FOREIGN KEY (estado_destino) REFERENCES Reserva_cud.Estado_reserva(sigla)
    );

    /* MS Reserva para Read */
    CREATE TABLE IF NOT EXISTS Reserva_read.Estado_reserva (
        sigla VARCHAR(5) PRIMARY KEY,
        descricao VARCHAR(50) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS Reserva_read.Reserva (
        cod VARCHAR(6) PRIMARY KEY,
        cod_estado VARCHAR(5) NOT NULL,
        cod_voo VARCHAR(8) NOT NULL,
        id_cliente UUID NOT NULL,
        data_hora TIMESTAMP NOT NULL,
        milhas DOUBLE PRECISION DEFAULT 0,
        valor DOUBLE PRECISION DEFAULT 0,
        CONSTRAINT fk_reserva_read_estado FOREIGN KEY (cod_estado) REFERENCES Reserva_read.Estado_reserva(sigla),
        CONSTRAINT fk_reserva_read_voo FOREIGN KEY (cod_voo) REFERENCES Voo.Voo(cod),
        CONSTRAINT fk_reserva_read_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente.Cliente(id)
    );

    INSERT INTO voo.aeroporto (cod, cidade, estado, nome) VALUES
        ('MCP', 'Amapá', 'AP', 'Aeroporto Amapá'),
        ('SWY', 'Apuí', 'AM', 'Aeroporto Apuí'),
        ('APQ', 'Arapiraca', 'AL', 'Aeroporto Arapiraca'),
        ('BAZ', 'Barcelos', 'AM', 'Aeroporto Barcelos'),
        ('BEL', 'Belém', 'PA', 'Aeroporto Belém'),
        ('CNF', 'Belo Horizonte', 'MG', 'Aeroporto Confins'),
        ('PLU', 'Belo Horizonte', 'MG', 'Aeroporto Pampulha'),
        ('BVB', 'Boa Vista', 'RR', 'Aeroporto Boa Vista'),
        ('BSB', 'Brasília', 'DF', 'Aeroporto Brasília'),
        ('CFC', 'Caçador', 'SC', 'Aeroporto Caçador'),
        ('VCP', 'Campinas', 'SP', 'Aeroporto Viracopos'),
        ('CAU', 'Caruaru', 'PE', 'Aeroporto Caruaru'),
        ('CAF', 'Carauari', 'AM', 'Aeroporto Carauari'),
        ('CAC', 'Cascavel', 'PR', 'Aeroporto Cascavel'),
        ('CIZ', 'Coari', 'AM', 'Aeroporto Coari'),
        ('CDJ', 'Conceição do Araguaia', 'PA', 'Aeroporto Conceição'),
        ('CZS', 'Cruzeiro do Sul', 'AC', 'Aeroporto Cruzeiro'),
        ('BFH', 'Curitiba', 'PR', 'Aeroporto Bacacheri'),
        ('CWB', 'Curitiba', 'PR', 'Aeroporto Afonso Pena'),
        ('FEJ', 'Feijó', 'AC', 'Aeroporto Feijó'),
        ('FLN', 'Florianópolis', 'SC', 'Aeroporto Hercílio Luz'),
        ('FOR', 'Fortaleza', 'CE', 'Aeroporto Pinto Martins'),
        ('IGU', 'Foz do Iguaçú', 'PR', 'Aeroporto Cataratas'),
        ('IZA', 'Goianá', 'MG', 'Aeroporto Itamar Franco'),
        ('GYN', 'Goiânia', 'GO', 'Aeroporto Santa Genoveva'),
        ('GRU', 'Guarulhos', 'SP', 'Aeroporto Guarulhos'),
        ('IMP', 'Imperatriz', 'MA', 'Aeroporto Imperatriz'),
        ('JJD', 'Jericoacoara', 'CE', 'Aeroporto Jericoacoara'),
        ('JOI', 'Joinville', 'SC', 'Aeroporto Joinville'),
        ('JPA', 'João Pessoa', 'PB', 'Aeroporto João Pessoa'),
        ('JDF', 'Juiz de Fora', 'MG', 'Aeroporto Juiz de Fora'),
        ('QDV', 'Jundiaí', 'SP', 'Aeroporto Jundiaí'),
        ('MAO', 'Manaus', 'AM', 'Aeroporto Manaus'),
        ('MCZ', 'Maceió', 'AL', 'Aeroporto Zumbi dos Palmares'),
        ('MVF', 'Mossoró', 'RN', 'Aeroporto Mossoró'),
        ('NVT', 'Navegantes', 'SC', 'Aeroporto Navegantes'),
        ('NAT', 'Natal', 'RN', 'Aeroporto Natal'),
        ('PHB', 'Parnaíba', 'PI', 'Aeroporto Parnaíba'),
        ('PGZ', 'Ponta Grossa', 'PR', 'Aeroporto Ponta Grossa'),
        ('POA', 'Porto Alegre', 'RS', 'Aeroporto Salgado Filho'),
        ('PVH', 'Porto Velho', 'RO', 'Aeroporto Porto Velho'),
        ('REC', 'Recife', 'PE', 'Aeroporto Recife'),
        ('RAO', 'Ribeirão Preto', 'SP', 'Aeroporto Ribeirão'),
        ('RBR', 'Rio Branco', 'AC', 'Aeroporto Rio Branco'),
        ('SDU', 'Rio de Janeiro', 'RJ', 'Aeroporto Santos Dumont'),
        ('GIG', 'Rio de Janeiro', 'RJ', 'Aeroporto Galeão'),
        ('ROO', 'Rondonópolis', 'MT', 'Aeroporto Rondonópolis'),
        ('RIA', 'Santa Maria', 'RS', 'Aeroporto Santa Maria'),
        ('SSZ', 'Santos', 'SP', 'Base Aérea de Santos'),
        ('SSA', 'Salvador', 'BA', 'Aeroporto Salvador'),
        ('QSC', 'São Carlos', 'SP', 'Aeroporto São Carlos'),
        ('SLZ', 'São Luís', 'MA', 'Aeroporto São Luís'),
        ('CGH', 'São Paulo', 'SP', 'Aeroporto Congonhas'),
        ('ZMD', 'Sena Madureira', 'AC', 'Aeroporto Sena Madureira'),
        ('SOD', 'Sorocaba', 'SP', 'Aeroporto Sorocaba'),
        ('TBT', 'Tabatinga', 'AM', 'Aeroporto Tabatinga'),
        ('TRQ', 'Tarauacá', 'AC', 'Aeroporto Tarauacá'),
        ('TFF', 'Tefé', 'AM', 'Aeroporto Tefé'),
        ('THE', 'Teresina', 'PI', 'Aeroporto Teresina');

    INSERT INTO Reserva_read.Estado_reserva (sigla, descricao) VALUES
        ('CONF', 'CONFIRMADO'),
        ('CHECK', 'CHECK IN'),
        ('CANC', 'CANCELADO'),
        ('CAVOO', 'CANCELADO VOO'),
        ('EMB', 'EMBARCADO'),
        ('RD', 'REALIZADO'),
        ('NRD', 'NÃO REALIZADO');

    INSERT INTO Reserva_cud.Estado_reserva (sigla, descricao) VALUES
        ('CONF', 'CONFIRMADO'),
        ('CHECK', 'CHECK IN'),
        ('CANC', 'CANCELADO'),
        ('CAVOO', 'CANCELADO VOO'),
        ('EMB', 'EMBARCADO'),
        ('RD', 'REALIZADO'),
        ('NRD', 'NÃO REALIZADO');
END
$$;
