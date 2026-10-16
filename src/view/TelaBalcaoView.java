
package view;

import controller.BalcaoController;
import model.ItemVenda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaBalcaoView extends JPanel {

    private final BalcaoController controller = new BalcaoController();

    private final JTextField busca = new JTextField(18);
    private final JTextField codigo = new JTextField(8);
    private final JTextField quantidade = new JTextField("1", 5);

    private final JLabel total = new JLabel("Total: R$ 0,00");
    private final JLabel totalItens = new JLabel("Total de itens: 0");

    private final DefaultTableModel produtos =
        new DefaultTableModel(
            new Object[]{"Código", "Produto", "Quantidade", "Vendas", "Preço"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

    private final DefaultTableModel carrinho =
        new DefaultTableModel(
            new Object[]{"Código", "Produto", "Qtde", "Preço", "Valor"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

    private final JTable tabelaProdutos = new JTable(produtos);
    private final JTable tabelaCarrinho = new JTable(carrinho);

    public TelaBalcaoView() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        criarPainelBusca();

        tabelaProdutos.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabelaProdutos.getSelectedRow();

                if (linha >= 0) {
                    codigo.setText(
                        tabelaProdutos.getValueAt(linha, 0).toString()
                    );
                }
            }
        });

        tabelaCarrinho.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        // Ao selecionar um item do carrinho, preenche o código.
        tabelaCarrinho.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabelaCarrinho.getSelectedRow();

                if (linha >= 0) {
                    codigo.setText(
                        tabelaCarrinho.getValueAt(linha, 0).toString()
                    );
                }
            }
        });

        add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);
        add(criarPainelCarrinho(), BorderLayout.EAST);

        criarPainelAcoes();
        carregarProdutos();
    }

    private void criarPainelBusca() {
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topo.add(new JLabel("Pesquisar:"));
        topo.add(busca);

        JButton pesquisar = new JButton("Buscar");
        pesquisar.addActionListener(e -> carregarProdutos());
        topo.add(pesquisar);

        // Novo botão: limpa a pesquisa.
        JButton limparBusca = new JButton("Limpar busca");
        limparBusca.addActionListener(e -> {
            busca.setText("");
            carregarProdutos();
            tabelaProdutos.clearSelection();
        });
        topo.add(limparBusca);

        add(topo, BorderLayout.NORTH);
    }

    private JPanel criarPainelCarrinho() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));

        painel.setBorder(
            BorderFactory.createTitledBorder("Carrinho de compras")
        );
        painel.setPreferredSize(new Dimension(340, 0));

        painel.add(
            new JScrollPane(tabelaCarrinho),
            BorderLayout.CENTER
        );

        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(
            new BoxLayout(painelInferior, BoxLayout.Y_AXIS)
        );

        totalItens.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelInferior.add(totalItens);
        painelInferior.add(Box.createVerticalStrut(8));

        total.setFont(total.getFont().deriveFont(Font.BOLD, 16f));
        total.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelInferior.add(total);
        painelInferior.add(Box.createVerticalStrut(12));

        JButton pagamento = new JButton("Ir para pagamento");
        pagamento.setAlignmentX(Component.CENTER_ALIGNMENT);
        pagamento.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 40)
        );
        pagamento.addActionListener(e -> abrirPagamento());

        painelInferior.add(pagamento);
        painel.add(painelInferior, BorderLayout.SOUTH);

        return painel;
    }

    private void criarPainelAcoes() {
        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT));

        acoes.add(new JLabel("Código:"));
        acoes.add(codigo);

        acoes.add(new JLabel("Quantidade:"));
        acoes.add(quantidade);

        JButton incluir = new JButton("Incluir no carrinho");
        incluir.addActionListener(e -> incluir());
        acoes.add(incluir);

        JButton removerCodigo = new JButton("Remover por código");
        removerCodigo.addActionListener(e -> remover());
        acoes.add(removerCodigo);

        // Novo botão: altera a quantidade da linha selecionada.
        JButton alterar = new JButton("Alterar quantidade");
        alterar.addActionListener(e -> alterarQuantidade());
        acoes.add(alterar);

        // Novo botão: remove a linha selecionada.
        JButton removerSelecionado = new JButton("Remover selecionado");
        removerSelecionado.addActionListener(e -> removerSelecionado());
        acoes.add(removerSelecionado);

        // Novo botão: esvazia o carrinho após confirmação.
        JButton limpar = new JButton("Limpar carrinho");
        limpar.addActionListener(e -> limparCarrinho());
        acoes.add(limpar);

        add(acoes, BorderLayout.SOUTH);
    }

    private void carregarProdutos() {
        produtos.setRowCount(0);

        controller.buscar(busca.getText()).forEach(p ->
            produtos.addRow(new Object[]{
                p.getCodigo(),
                p.getProduto(),
                p.getQuantidade(),
                p.getVendas(),
                String.format("R$ %.2f", p.getPreco())
            })
        );
    }

    private void incluir() {
        try {
            double qtd = Double.parseDouble(
                quantidade.getText().trim().replace(",", ".")
            );

            controller.adicionar(codigo.getText().trim(), qtd);

            atualizarCarrinho();
            atualizarTotal();
            quantidade.setText("1");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Digite uma quantidade válida.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void remover() {
        String codigoProduto = codigo.getText().trim();

        if (codigoProduto.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Informe o código do produto que deseja remover.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        controller.remover(codigoProduto);
        atualizarCarrinho();
        atualizarTotal();
    }

    private void alterarQuantidade() {
        int linha = tabelaCarrinho.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Selecione um produto no carrinho.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigoProduto =
            tabelaCarrinho.getValueAt(linha, 0).toString();

        String entrada = JOptionPane.showInputDialog(
            this,
            "Digite a nova quantidade:",
            tabelaCarrinho.getValueAt(linha, 2)
        );

        // Cancelar ou deixar vazio não altera nada.
        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            double novaQuantidade = Double.parseDouble(
                entrada.trim().replace(",", ".")
            );

            controller.alterarQuantidade(
                codigoProduto,
                novaQuantidade
            );

            atualizarCarrinho();
            atualizarTotal();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Digite uma quantidade numérica válida.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void removerSelecionado() {
        int linha = tabelaCarrinho.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Selecione um produto no carrinho.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigoProduto =
            tabelaCarrinho.getValueAt(linha, 0).toString();

        controller.remover(codigoProduto);

        atualizarCarrinho();
        atualizarTotal();
    }

    private void limparCarrinho() {
        if (controller.getCarrinho().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "O carrinho já está vazio.",
                "Carrinho",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente remover todos os itens do carrinho?",
            "Confirmar limpeza",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            controller.limparCarrinho();

            atualizarCarrinho();
            atualizarTotal();

            codigo.setText("");
            quantidade.setText("1");
        }
    }

    private void atualizarCarrinho() {
        carrinho.setRowCount(0);

        double quantidadeTotal = 0;

        for (ItemVenda item : controller.getCarrinho()) {
            quantidadeTotal += item.getQuantidade();

            carrinho.addRow(new Object[]{
                item.getProduto().getCodigo(),
                item.getProduto().getProduto(),
                item.getQuantidade(),
                String.format("R$ %.2f", item.getProduto().getPreco()),
                String.format("R$ %.2f", item.getSubtotal())
            });
        }

        totalItens.setText(
            String.format("Total de itens: %.0f", quantidadeTotal)
        );
    }

    private void atualizarTotal() {
        total.setText(
            String.format("Total: R$ %.2f", controller.calcularTotal())
        );
    }

    private void abrirPagamento() {
        if (controller.getCarrinho().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "O carrinho está vazio.",
                "Pagamento",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JFrame janela = new JFrame("Tela de Pagamento");
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        janela.add(
            new TelaPagamentoView(controller.calcularTotal())
        );

        janela.pack();
        janela.setMinimumSize(new Dimension(650, 300));
        janela.setLocationRelativeTo(
            SwingUtilities.getWindowAncestor(this)
        );
        janela.setVisible(true);
    }
}