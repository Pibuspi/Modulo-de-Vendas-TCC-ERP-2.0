# Módulo de Vendas (TCC ERP) - 2.0

Estrutura inicial de um sistema Java Swing para o módulo comercial, organizada em **Model-View-Controller + DAO**, com persistência planejada em PostgreSQL via JDBC. O escopo contempla cadastro, digitação, consulta e acompanhamento de pedidos, liberação comercial, devoluções e operação de balcão/PDV. O motor de precificação pertence ao Módulo Financeiro e não faz parte deste repositório.

## Estrutura

```text
Modulo-de-Vendas-TCC-ERP-2.0/
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

## Matriz atual de responsabilidades

| Integrante | Camada principal | Responsabilidade / telas |
|---|---|---|
| Eduardo Yuri | `src/model/` | Entidades de domínio compartilhadas: `Cliente`, `Produto`, `PedidoVenda`, `ItemPedido`, `Devolucao`, `LoteConsolidacao` e `CaixaBalcao`. |
| Rafael | `src/dao/` e `banco/` | Scripts SQL (`schema.sql`), conexão JDBC e DAOs: `ClienteDAO`, `PedidoDAO`, `DevolucaoDAO` e `CaixaDAO`. |
| Matheus Godoy | `src/controller/` | `PedidoController.java`, `ConsultaVendasController.java` e a view de Histórico/Dashboard de Vendas. |
| Lucas de Lima | `src/controller/` | `LiberacaoComercialController.java`, com cockpit de consolidação e liberação comercial. |
| Eúde | `src/view/` | `CadastroClienteView.java` e `TelaPrincipalView.java`, incluindo Dashboard/Launcher Central. |
| Pietro | `src/view/` | `GestaoDevolucoesView.java` e `DigitacaoPedidoView.java`. |
| Eduardo | `src/view/` | `TelaBalcaoView.java`, com abertura/fechamento de caixa e pagamentos. |

## Consulta, histórico e acompanhamento de pedidos

`ConsultaHistoricoVendasView.java` é a tela de acompanhamento do ciclo de vendas. O esqueleto contém filtros por período, cliente, vendedor e status do pedido: `ABERTO`, `BLOQUEADO`, `PENDENTE_APROVACAO`, `LIBERADO_PARA_FATURAMENTO` e `DEVOLVIDO_AO_VENDEDOR`. A interface também prevê uma `JTable` para pedidos, itens e valores totais, além de cards de KPI para total vendido no período, pedidos em aberto e pedidos bloqueados.

`ConsultaVendasController.java`, em parceria com Lucas de Lima, concentra a consulta filtrada, o cálculo dos indicadores e a alimentação da view. A implementação definitiva deverá delegar a consulta parametrizada ao `PedidoDAO`.

## Regras básicas de pedidos

`PedidoController.java`, sob responsabilidade de Matheus Godoy em parceria com Lucas de Lima, contém os pontos de extensão para adição e remoção de itens, cálculo de subtotal e total do pedido, validação de estoque e regras comerciais básicas de Vendas.

## Camadas

### Model — Eduardo Yuri

As entidades representam o domínio compartilhado: cliente com dados cadastrais e restrições, produto com preço/custo/estoque, item e cabeçalho de pedido, devolução, lote de consolidação e caixa de balcão.

### DAO e banco — Rafael

`banco/schema.sql` contém a DDL inicial para PostgreSQL. `Conexao.java` usa `DB_URL`, `DB_USER` e `DB_PASSWORD` do ambiente. Os DAOs são contratos de esqueleto para CRUDs e consultas JDBC parametrizadas.

### Controllers — Matheus Godoy e Lucas de Lima

- `PedidoController`: adiciona/remove itens, calcula subtotais e totais, valida estoque e regras básicas.
- `ConsultaVendasController`: filtra e lista pedidos e calcula os KPIs do histórico/dashboard.
- `LiberacaoComercialController`: filtra pedidos, valida checklist e altera o status comercial.
- `ClienteController`: valida CPF/CNPJ, razão social, limite e restrições.
- `DevolucaoController`: valida elegibilidade, prazo e ordem comercial de devolução.

### Views — equipe de interface

- `TelaPrincipalView`: launcher central das seis áreas do módulo.
- `ConsultaHistoricoVendasView`: filtros, tabela de pedidos e KPIs do ciclo de vendas.
- `CadastroClienteView`: dados cadastrais, regras comerciais e configuração fiscal.
- `DigitacaoPedidoView`: lançamento de pedidos e atualização de totais.
- `LiberacaoComercialView`: cockpit de consolidação e liberação.
- `GestaoDevolucoesView`: solicitação, análise comercial e ordem comercial.
- `TelaBalcaoView`: PDV, caixa, pagamentos e troco.

## Como executar

Com JDK instalado:

```bash
mkdir -p bin
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin Main
```

A conexão PostgreSQL depende da implementação dos DAOs e das variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD`. Não versionar credenciais.

## Convenções

- Pacotes seguem a camada: `model`, `dao`, `controller` e `view`; `Main` permanece no pacote padrão para facilitar o launcher do Eclipse.
- Controllers concentram regras de negócio; Views não executam SQL.
- Operações que alteram múltiplas tabelas devem usar transação explícita.
- O motor de precificação pertence ao Módulo Financeiro e não deve ser reintroduzido neste repositório.
