package view;

import controller.BalcaoController;
import model.ItemVenda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Eduardo Yuri: PDV com busca, tabela de produtos,
 * carrinho à direita e acesso ao pagamento.
 */
public class TelaBalcaoView extends JPanel {

    private final BalcaoController controller = new BalcaoController();

    // =====================================================
    // CAMPOS
    // =====================================================

    private final JTextField busca = new JTextField(18);

    private final JTextField codigo = new JTextField(8);

    private final JTextField quantidade =
            new JTextField("1", 5);

    // =====================================================
    // TOTAIS
    // =====================================================

    private final JLabel total =
            new JLabel("Total: R$ 0,00");

    private final JLabel totalItens =
            new JLabel("Total de itens: 0");

    // =====================================================
    // TABELA DE PRODUTOS
    // =====================================================

    private final DefaultTableModel produtos =
            new DefaultTableModel(
                    new Object[]{
                            "Código",
                            "Produto",
                            "Quantidade",
                            "Vendas",
                            "Preço"
                    },
                    0
            );

    // =====================================================
    // TABELA DO CARRINHO
    // =====================================================

    private final DefaultTableModel carrinho =
            new DefaultTableModel(
                    new Object[]{
                            "Código",
                            "Produto",
                            "Qtde",
                            "Preço",
                            "Valor"
                    },
                    0
            );

    private final JTable tabelaCarrinho =
            new JTable(carrinho);

    // =====================================================
    // CONSTRUTOR
    // =====================================================

    public TelaBalcaoView() {

        setLayout(
                new BorderLayout(8, 8)
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        // =================================================
        // PARTE SUPERIOR - PESQUISA
        // =================================================

        JPanel topo =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        topo.add(
                new JLabel("Pesquisar:")
        );

        topo.add(busca);

        JButton pesquisar =
                new JButton("Buscar");

        pesquisar.addActionListener(
                e -> carregarProdutos()
        );

        topo.add(pesquisar);

        add(
                topo,
                BorderLayout.NORTH
        );

        // =================================================
        // TABELA DE PRODUTOS
        // =================================================

        JTable tabelaProdutos =
                new JTable(produtos);

        tabelaProdutos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        /*
         * Quando o usuário clicar em um produto,
         * o código dele será colocado automaticamente
         * no campo "Código".
         */
        tabelaProdutos
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int linha =
                                tabelaProdutos
                                        .getSelectedRow();

                        if (linha >= 0) {

                            String codigoProduto =
                                    tabelaProdutos
                                            .getValueAt(
                                                    linha,
                                                    0
                                            )
                                            .toString();

                            codigo.setText(
                                    codigoProduto
                            );
                        }
                    }
                });

        JScrollPane scrollProdutos =
                new JScrollPane(
                        tabelaProdutos
                );

        // =================================================
        // PRODUTOS NO CENTRO
        // =================================================

        add(
                scrollProdutos,
                BorderLayout.CENTER
        );

        // =================================================
        // CARRINHO NA DIREITA
        // =================================================

        add(
                criarPainelCarrinho(),
                BorderLayout.EAST
        );

        // =================================================
        // PARTE INFERIOR DA TELA
        // =================================================

        JPanel acoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        // Código
        acoes.add(
                new JLabel("Código:")
        );

        acoes.add(codigo);

        // Quantidade
        acoes.add(
                new JLabel("Quantidade:")
        );

        acoes.add(quantidade);

        // =================================================
        // BOTÃO INCLUIR
        // =================================================

        JButton incluir =
                new JButton(
                        "Incluir no carrinho"
                );

        incluir.addActionListener(
                e -> incluir()
        );

        acoes.add(incluir);

        // =================================================
        // BOTÃO REMOVER
        // =================================================

        JButton remover =
                new JButton(
                        "Remover por código"
                );

        remover.addActionListener(e -> {

            controller.remover(
                    codigo.getText()
            );

            atualizarCarrinho();

            atualizarTotal();
        });

        acoes.add(remover);

        // Coloca os controles na parte inferior
        add(
                acoes,
                BorderLayout.SOUTH
        );

        // =================================================
        // CARREGA OS PRODUTOS
        // =================================================

        carregarProdutos();
    }

    // =====================================================
    // CRIAR PAINEL DO CARRINHO
    // =====================================================

    private JPanel criarPainelCarrinho() {

        JPanel painel =
                new JPanel(
                        new BorderLayout(5, 5)
                );

        painel.setBorder(
                BorderFactory.createTitledBorder(
                        "Carrinho"
                )
        );

        /*
         * Define a largura da coluna direita.
         */
        painel.setPreferredSize(
                new Dimension(
                        320,
                        0
                )
        );

        // =================================================
        // TABELA DO CARRINHO
        // =================================================

        tabelaCarrinho.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabelaCarrinho.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        JScrollPane scrollCarrinho =
                new JScrollPane(
                        tabelaCarrinho
                );

        /*
         * A tabela ocupa o centro do painel.
         */
        painel.add(
                scrollCarrinho,
                BorderLayout.CENTER
        );

        // =================================================
        // PARTE INFERIOR DO CARRINHO
        // =================================================

        JPanel painelInferior =
                new JPanel();

        painelInferior.setLayout(
                new BoxLayout(
                        painelInferior,
                        BoxLayout.Y_AXIS
                )
        );

        // =================================================
        // TOTAL DE ITENS
        // =================================================

        totalItens.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painelInferior.add(
                totalItens
        );

        painelInferior.add(
                Box.createVerticalStrut(5)
        );

        // =================================================
        // TOTAL
        // =================================================

        total.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painelInferior.add(
                total
        );

        painelInferior.add(
                Box.createVerticalStrut(10)
        );

        // =================================================
        // BOTÃO PAGAMENTO
        // =================================================

        JButton pagamento =
                new JButton(
                        "Ir para pagamento"
                );

        pagamento.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        /*
         * Faz o botão ocupar toda a largura
         * disponível do painel.
         */
        pagamento.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        pagamento.addActionListener(
                e -> abrirPagamento()
        );

        painelInferior.add(
                pagamento
        );

        // =================================================
        // COLOCA O PAINEL INFERIOR ABAIXO DA TABELA
        // =================================================

        painel.add(
                painelInferior,
                BorderLayout.SOUTH
        );

        return painel;
    }

    // =====================================================
    // CARREGAR PRODUTOS
    // =====================================================

    private void carregarProdutos() {

        produtos.setRowCount(0);

        controller
                .buscar(busca.getText())
                .forEach(p ->
                        produtos.addRow(
                                new Object[]{
                                        p.getCodigo(),
                                        p.getProduto(),
                                        p.getQuantidade(),
                                        p.getVendas(),
                                        String.format(
                                                "R$ %.2f",
                                                p.getPreco()
                                        )
                                }
                        )
                );
    }

    // =====================================================
    // INCLUIR PRODUTO NO CARRINHO
    // =====================================================

    private void incluir() {

        try {

            double qtd =
                    Double.parseDouble(
                            quantidade.getText()
                    );

            controller.adicionar(
                    codigo.getText(),
                    qtd
            );

            // Atualiza a tabela do carrinho
            atualizarCarrinho();

            // Atualiza o valor total
            atualizarTotal();

        } catch (RuntimeException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =====================================================
    // ATUALIZAR TABELA DO CARRINHO
    // =====================================================

    private void atualizarCarrinho() {

        // Remove as linhas antigas
        carrinho.setRowCount(0);

        double quantidadeTotal = 0;

        // Percorre todos os produtos do carrinho
        for (ItemVenda item :
                controller.getCarrinho()) {

            quantidadeTotal +=
                    item.getQuantidade();

            carrinho.addRow(
                    new Object[]{
                            item.getProduto()
                                    .getCodigo(),

                            item.getProduto()
                                    .getProduto(),

                            item.getQuantidade(),

                            String.format(
                                    "R$ %.2f",
                                    item.getProduto()
                                            .getPreco()
                            ),

                            String.format(
                                    "R$ %.2f",
                                    item.getSubtotal()
                            )
                    }
            );
        }

        // Atualiza quantidade total de itens
        totalItens.setText(
                String.format(
                        "Total de itens: %.0f",
                        quantidadeTotal
                )
        );
    }

    // =====================================================
    // ATUALIZAR TOTAL DA VENDA
    // =====================================================

    private void atualizarTotal() {

        total.setText(
                String.format(
                        "Total: R$ %.2f",
                        controller.calcularTotal()
                )
        );
    }

    // =====================================================
    // ABRIR TELA DE PAGAMENTO
    // =====================================================

    private void abrirPagamento() {

        // Não permite pagamento com carrinho vazio
        if (controller.getCarrinho().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "O carrinho está vazio.",
                    "Pagamento",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JFrame janela =
                new JFrame(
                        "Tela de Pagamento"
                );

        janela.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        janela.add(
                new TelaPagamentoView(
                        controller.calcularTotal()
                )
        );

        janela.pack();

        janela.setLocationRelativeTo(this);

        janela.setVisible(true);
    }
}