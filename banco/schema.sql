-- PostgreSQL - Módulo de Vendas TCC ERP 2.0
CREATE TABLE IF NOT EXISTS cliente (
    id BIGSERIAL PRIMARY KEY,
    cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
    razao_social VARCHAR(200) NOT NULL,
    nome_fantasia VARCHAR(200),
    email VARCHAR(200), telefone VARCHAR(30),
    inscricao_estadual VARCHAR(30), inscricao_municipal VARCHAR(30),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    limite_credito NUMERIC(15,2) NOT NULL DEFAULT 0,
    condicao_pagamento VARCHAR(100), vendedor_responsavel VARCHAR(150),
    canal_venda VARCHAR(50), segmentacao VARCHAR(80),
    bloqueado BOOLEAN NOT NULL DEFAULT FALSE,
    bloqueado_por_inadimplencia BOOLEAN NOT NULL DEFAULT FALSE,
    regra_fiscal_pendente BOOLEAN NOT NULL DEFAULT FALSE,
    motivo_bloqueio VARCHAR(500), criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS produto (
    id BIGSERIAL PRIMARY KEY, sku VARCHAR(60) NOT NULL UNIQUE,
    descricao VARCHAR(250) NOT NULL, unidade_medida VARCHAR(10) NOT NULL,
    preco_tabela NUMERIC(15,2) NOT NULL DEFAULT 0, custo NUMERIC(15,2) NOT NULL DEFAULT 0,
    estoque_disponivel NUMERIC(15,3) NOT NULL DEFAULT 0, ncm VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS pedido_venda (
    id BIGSERIAL PRIMARY KEY, numero_pedido VARCHAR(40), cliente_id BIGINT NOT NULL REFERENCES cliente(id),
    data_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, vendedor VARCHAR(150),
    condicao_pagamento VARCHAR(100), canal_venda VARCHAR(50), observacoes VARCHAR(1000),
    status_comercial VARCHAR(30) NOT NULL DEFAULT 'ABERTO',
    total_bruto NUMERIC(15,2) NOT NULL DEFAULT 0, total_desconto NUMERIC(15,2) NOT NULL DEFAULT 0,
    total_liquido NUMERIC(15,2) NOT NULL DEFAULT 0, criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS item_pedido (
    id BIGSERIAL PRIMARY KEY, pedido_id BIGINT NOT NULL REFERENCES pedido_venda(id),
    produto_id BIGINT NOT NULL REFERENCES produto(id), quantidade NUMERIC(15,3) NOT NULL,
    preco_unitario NUMERIC(15,2) NOT NULL, desconto NUMERIC(15,2) NOT NULL DEFAULT 0,
    desconto_percentual NUMERIC(7,3) NOT NULL DEFAULT 0, subtotal NUMERIC(15,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS devolucao (
    id BIGSERIAL PRIMARY KEY, pedido_id BIGINT NOT NULL REFERENCES pedido_venda(id),
    motivo VARCHAR(1000) NOT NULL, data_solicitacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    valor_estorno NUMERIC(15,2) NOT NULL DEFAULT 0, status VARCHAR(30) NOT NULL DEFAULT 'SOLICITADO',
    caminho_anexo_evidencia VARCHAR(1000), parecer_gerencial VARCHAR(2000), ordem_estorno VARCHAR(80)
);

CREATE TABLE IF NOT EXISTS item_devolucao (
    id BIGSERIAL PRIMARY KEY, devolucao_id BIGINT NOT NULL REFERENCES devolucao(id),
    produto_id BIGINT NOT NULL REFERENCES produto(id), quantidade NUMERIC(15,3) NOT NULL,
    elegivel BOOLEAN NOT NULL DEFAULT FALSE, motivo VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS cliente_pagamento (
    id BIGSERIAL PRIMARY KEY, cpf VARCHAR(20), nome VARCHAR(200) NOT NULL,
    ddd VARCHAR(4), telefone VARCHAR(20), cep VARCHAR(12), endereco VARCHAR(250),
    numero VARCHAR(20), uf CHAR(2) NOT NULL DEFAULT 'DF'
);

CREATE TABLE IF NOT EXISTS regra_preco (
    id BIGSERIAL PRIMARY KEY, produto_id BIGINT REFERENCES produto(id), sku VARCHAR(60),
    preco_custo NUMERIC(15,2) NOT NULL DEFAULT 0, preco_minimo NUMERIC(15,2),
    margem_minima_percentual NUMERIC(7,3) NOT NULL DEFAULT 0,
    desconto_maximo_automatico NUMERIC(7,3), desconto_maximo_gerente NUMERIC(7,3),
    nome_tabela VARCHAR(120), regiao VARCHAR(80), canal_venda VARCHAR(50),
    vigencia_inicial DATE, vigencia_final DATE, status_tabela VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS auditoria_pricing (
    id BIGSERIAL PRIMARY KEY, regra_id BIGINT REFERENCES regra_preco(id),
    pedido_id BIGINT REFERENCES pedido_venda(id), usuario_id BIGINT,
    data_auditoria TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, acao VARCHAR(80),
    desconto_percentual NUMERIC(7,3), preco_praticado NUMERIC(15,2), margem_real NUMERIC(7,3),
    status_resultado VARCHAR(40), justificativa VARCHAR(2000)
);
