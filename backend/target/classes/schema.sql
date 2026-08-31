DROP TABLE IF EXISTS Comercio;
DROP TABLE IF EXISTS Cidade;

CREATE TABLE Cidade (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        nome VARCHAR(100) NOT NULL,
                        uf VARCHAR(2) NOT NULL,
                        capital BOOLEAN NOT NULL,
                        PRIMARY KEY (id)
);

CREATE TABLE Comercio (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          nome_comercio VARCHAR(100) NOT NULL,
                          nome_responsavel VARCHAR(100) NOT NULL,
                          tipo_comercio VARCHAR(30) NOT NULL,
                          cidade_id BIGINT NOT NULL,
                          PRIMARY KEY (id),

                          CONSTRAINT fk_comercio_cidade
                              FOREIGN KEY (cidade_id)
                                  REFERENCES Cidade(id)
);