# Vendas_TCC — Módulo de Vendas (TCC ERP) 2.0

Estrutura inicial de um sistema Java Swing para o módulo comercial, organizada em **Model-View-Controller + DAO**. O projeto foi preparado para uso no Eclipse e para persistência em PostgreSQL via JDBC.

## Objetivo

Disponibilizar uma base compartilhada para cadastro de clientes e produtos, digitação e liberação de pedidos, precificação, devoluções e operação de balcão/PDV. Os arquivos Java deste primeiro esqueleto contêm contratos, atributos essenciais e comentários de responsabilidade; a implementação das regras e dos CRUDs deverá evoluir por camada.

## Estrutura

```text
Vendas_TCC/
├── .gitignore
├── README.md
├── banco/schema.sql
├── bin/
└── src/
    ├── Main.java
    ├── model/
    ├── dao/
    ├── controller/
    └── view/
```

## Arquitetura e divisão de responsabilidades

| Camada | Responsáveis | Escopo |
|---|---|---|
| `src/model/` | Eduardo Yuri | Entidades de domínio compartilhadas, estado comercial e cálculos simples da entidade. |
| `src/dao/` e `banco/` | Rafael | Conexão, DDL PostgreSQL, CRUDs e consultas JDBC. |
| `src/controller/` | Matheus Godoy e Lucas de Lima | Regras de negócio, validações, totais, margem, hard stops, status e transações. |
| `src/view/` | Eúde e Pietro | Interfaces Swing, navegação, tabelas, abas, cabeçalhos, KPIs e badges de status. |

### Model — Eduardo Yuri

- `Cliente.java`: CPF/CNPJ, razão social, nome fantasia, limite de crédito, condição de pagamento e bloqueios.
- `Produto.java`: SKU, descrição, unidade, preço de tabela, custo, estoque disponível e NCM.
- `ItemPedido.java`: quantidade, preço, desconto e subtotal.
- `PedidoVenda.java`: cabeçalho, itens, totais e status comercial.
- `RegraPreco.java`, `TabelaPreco.java` e `AlcadaComercial.java`: margem e limites de desconto.
- `LoteConsolidacaoComercial.java`: agrupamento de pedidos para liberação.
- `Devolucao.java` e `ItemDevolucao.java`: motivos, parecer e ordens de estorno.

### DAO e banco — Rafael

`banco/schema.sql` contém a base DDL inicial para PostgreSQL. `Conexao.java` centraliza a abertura de conexões por propriedades de ambiente (`DB_URL`, `DB_USER`, `DB_PASSWORD`). Os DAOs fornecidos (`ClienteDAO`, `ProdutoDAO`, `PedidoDAO`, `AlcadaDAO`, `DevolucaoDAO`) são contratos de esqueleto para CRUDs e consultas JDBC.

### Controllers — Matheus Godoy e Lucas de Lima

- `ClienteController`: valida CPF/CNPJ, razão social, limite e restrições.
- `PedidoController`: adiciona/remove itens, calcula totais, valida estoque e limites.
- `PricingEngineController`: classifica margem: até 5% liberado; acima de 5% até 10% pendente; acima de 10% hard stop.
- `LiberacaoComercialController`: filtra pedidos, valida checklist e altera status.
- `DevolucaoController`: valida elegibilidade, prazo e ordem comercial de devolução.

### Views — Eúde e Pietro

`TelaPrincipalView` navega para as seis áreas principais: cadastro de cliente, digitação de pedido, pricing, liberação comercial, devoluções e balcão/PDV. As demais views já possuem pontos de entrada para cabeçalhos, tabelas, abas, filtros, cards e ações.

## Como executar

Com JDK instalado:

```bash
mkdir -p bin
javac -d bin $(find src -name "*.java")
java -cp bin Main
```

A conexão PostgreSQL ainda depende da implementação dos DAOs e das variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD`. Não versionar credenciais.

## Convenções

- Pacotes seguem a camada: `model`, `dao`, `controller` e `view`; `Main` permanece no pacote padrão para facilitar o launcher do Eclipse.
- Controllers devem concentrar regras de negócio; Views não devem executar SQL.
- Operações que alteram múltiplas tabelas devem usar transação explícita.
- Toda evolução deve preservar os hard stops comerciais e registrar validações relevantes.
