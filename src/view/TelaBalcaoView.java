package view;

import controller.BalcaoController;
import model.ItemVenda;
import model.Produto;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Lucas: catálogo virtual/PDV, carrinho em memória e acesso ao checkout. */
public class TelaBalcaoView extends JPanel {
    private final BalcaoController controller = new BalcaoController();
    private final JTextField busca = new JTextField(22);
    private final JTextField codigo = new JTextField(10);
    private final JTextField quantidade = new JTextField("1", 6);
    private final JLabel total = new JLabel(Formato.moeda(0));
    private final DefaultTableModel produtosModel = new DefaultTableModel(
        new Object[]{"Código", "Produto", "Quantidade", "Vendas", "Preço"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    private final DefaultTableModel carrinhoModel = new DefaultTableModel(
        new Object[]{"Código", "Produto", "Quantidade", "Preço", "Subtotal"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    private final JTable tabelaProdutos = new JTable(produtosModel);
    private final JTable tabelaCarrinho = new JTable(carrinhoModel);

    public TelaBalcaoView() {
        Tema.aplicar();
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(criarBusca(), BorderLayout.NORTH);
        add(criarCatalogo(), BorderLayout.CENTER);
        add(criarCarrinho(), BorderLayout.SOUTH);
        carregarProdutos();
    }

    private JPanel criarBusca() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBorder(BorderFactory.createTitledBorder("Catálogo de Produtos"));
        painel.add(new JLabel("Buscar por código, nome ou preço:")); painel.add(busca);
        JButton atualizar = new JButton("Buscar"); atualizar.addActionListener(evento -> carregarProdutos()); painel.add(atualizar);
        busca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { carregarProdutos(); }
            public void removeUpdate(DocumentEvent e) { carregarProdutos(); }
            public void changedUpdate(DocumentEvent e) { carregarProdutos(); }
        });
        return painel;
    }

    private JScrollPane criarCatalogo() {
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.setFillsViewportHeight(true);
        tabelaProdutos.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting() && tabelaProdutos.getSelectedRow() >= 0) {
                codigo.setText(String.valueOf(produtosModel.getValueAt(tabelaProdutos.getSelectedRow(), 0)));
            }
        });
        return new JScrollPane(tabelaProdutos);
    }

    private JPanel criarCarrinho() {
        JPanel painel = new JPanel(new BorderLayout(6, 6));
        painel.setBorder(BorderFactory.createTitledBorder("Carrinho de Compras"));
        painel.add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);
        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acoes.add(new JLabel("Código:")); acoes.add(codigo); acoes.add(new JLabel("Quantidade:")); acoes.add(quantidade);
        JButton incluir = new JButton("Adicionar ao Carrinho"); incluir.addActionListener(evento -> incluir()); acoes.add(incluir);
        JButton remover = new JButton("Remover Item Selecionado"); remover.addActionListener(evento -> remover()); acoes.add(remover);
        total.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12)); acoes.add(new JLabel("Total da Compra:")); acoes.add(total);
        JButton pagamento = new JButton("Ir para Pagamento / Checkout"); pagamento.addActionListener(evento -> abrirPagamento()); acoes.add(pagamento);
        painel.add(acoes, BorderLayout.SOUTH);
        return painel;
    }

    private void carregarProdutos() {
        produtosModel.setRowCount(0);
        for (Produto produto : controller.buscar(busca.getText())) {
            produtosModel.addRow(new Object[]{produto.getCodigo(), produto.getProduto(), produto.getQuantidade(), produto.getVendas(), Formato.moeda(produto.getPreco())});
        }
        if (controller.getUltimoErro() != null) setToolTipText(controller.getUltimoErro());
    }

    private void incluir() {
        try {
            controller.adicionar(codigo.getText(), Formato.lerNumero(quantidade.getText()));
            atualizarCarrinho();
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Carrinho", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void remover() {
        try { controller.remover(tabelaCarrinho.getSelectedRow()); atualizarCarrinho(); }
        catch (RuntimeException erro) { JOptionPane.showMessageDialog(this, erro.getMessage(), "Carrinho", JOptionPane.WARNING_MESSAGE); }
    }

    private void atualizarCarrinho() {
        carrinhoModel.setRowCount(0);
        for (ItemVenda item : controller.getCarrinho()) {
            carrinhoModel.addRow(new Object[]{item.getProduto().getCodigo(), item.getProduto().getProduto(), item.getQuantidade(),
                Formato.moeda(item.getProduto().getPreco()), Formato.moeda(item.getSubtotal())});
        }
        total.setText(Formato.moeda(controller.calcularTotal()));
    }

    private void abrirPagamento() {
        if (controller.getCarrinho().isEmpty()) { JOptionPane.showMessageDialog(this, "Adicione ao menos um item ao carrinho.", "Checkout", JOptionPane.WARNING_MESSAGE); return; }
        JFrame janela = new JFrame("Tela de Pagamento"); janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.add(new TelaPagamentoView(controller.getCarrinho(), controller.calcularTotal())); janela.pack(); janela.setLocationRelativeTo(this); janela.setVisible(true);
    }
}
