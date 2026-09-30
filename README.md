# Módulo de Vendas (TCC ERP) - 2.0

Sistema Java Swing organizado em **Model-View-Controller + DAO**, com persistência planejada em PostgreSQL via JDBC. O escopo atual reúne cadastro de clientes, digitação de pedidos, motor de preços e alçadas, liberação comercial, devoluções e operação de balcão com pagamento.

## Estrutura

```text
Modulo-de-Vendas-TCC-ERP-2.0/
├── .gitignore
├── README.md
├── banco/schema.sql
└── src/
    ├── Main.java
    ├── model/
    ├── dao/
    ├── controller/
    └── view/
```

Todo o código funcional está unificado sob `src/`. Não existem subprojetos soltos ou pastas paralelas de telas.

## Matriz oficial de responsabilidades

| Integrante | Módulo / Tela | `model/` | `controller/` | `view/` | `dao/` & `banco/` |
|---|---|---|---|---|---|
| Eúde | Cadastro de Clientes | `Cliente.java` | `ClienteController.java` (validações & status) | `CadastroClienteView.java` | `ClienteDAO.java` |
| Rafael | Digitação de Pedidos | `PedidoVenda.java`, `ItemPedido.java` | `PedidoController.java` (itens & totais) | `DigitacaoPedidoView.java` | `PedidoDAO.java` |
| Matheus Godoy | Motor de Preços (Pricing) | `RegraPreco.java`, `AlcadaComercial.java`, `ResultadoPrecificacao.java` | `PricingEngineController.java` (margens & hard stops) | `PricingEngineView.java` | `AlcadaDAO.java` |
| Lucas de Lima | Liberação Comercial | `LoteConsolidacaoComercial.java` | `LiberacaoComercialController.java` (status & cockpit) | `LiberacaoComercialView.java` | Atualizações em `PedidoDAO` |
| Pietro | Gestão de Devoluções | `Devolucao.java`, `ItemDevolucao.java` | `DevolucaoController.java` (prazos & elegibilidade) | `GestaoDevolucoesView.java` | `DevolucaoDAO.java` |
| Eduardo Yuri | Tela de Balcão & Pagamento | `Produto.java`, `ItemVenda.java`, `ClientePagamento.java` | `BalcaoController.java`, `TelaPagamentoController.java` | `TelaBalcaoView.java`, `TelaPagamentoView.java` | `VendaBalcaoDAO.java` |

## Motor de Preços e Alçadas Comerciais

`PricingEngineController.validarMargemLucro(custo, precoVenda, desconto)` calcula o preço praticado e a margem real de lucro. As travas de governança são:

- **Desconto até 5%:** `LIBERADO`, com aprovação automática.
- **Desconto de 5,01% a 10%:** `PENDENTE_APROVACAO`, exigindo parecer gerencial.
- **Desconto acima de 10% ou margem real negativa:** `HARD_STOP`, bloqueio imediato.

`PricingEngineView` apresenta o resumo das regras, campos de custo, preço e desconto, margem calculada e badge visual do status da operação. A tela está disponível no menu principal.

## Balcão e Pagamento

### Entidade Produto

A entidade `Produto` de balcão possui estritamente os cinco campos definidos: `codigo`, `produto`, `quantidade`, `vendas` e `preco`.

### Tela de Balcão

`TelaBalcaoView` oferece busca por código, nome ou preço, `JTable` com as colunas Código, Produto, Quantidade, Vendas e Preço, inclusão no carrinho, remoção por código, totalização pela soma das quantidades e atalho para pagamento. `BalcaoController` mantém a seleção de `ItemVenda`, confirma/retira itens e calcula o total.

### Tela de Pagamento

`ClientePagamento` possui os oito campos oficiais: `cpf`, `nome`, `ddd`, `telefone`, `cep`, `endereco`, `numero` e `uf`, cujo padrão é `DF`. `TelaPagamentoView` apresenta o formulário, recebimentos em Dinheiro, PIX e Cartão, total, valor pago, troco e confirmação. `TelaPagamentoController` valida o consumidor, soma múltiplos meios e impede a confirmação quando o valor recebido é insuficiente.

## Como executar

Com JDK instalado:

```bash
mkdir -p bin
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin Main
```

A conexão PostgreSQL depende das variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD`. Não versionar credenciais.

## Convenções

- Pacotes seguem a camada: `model`, `dao`, `controller` e `view`; `Main` permanece no pacote padrão para o launcher do Eclipse.
- Controllers concentram regras de negócio; Views não executam SQL.
- Operações que alteram múltiplas tabelas devem usar transação explícita.
- O fluxo de pagamento somente confirma vendas após validar dados do consumidor e recebimento total.
