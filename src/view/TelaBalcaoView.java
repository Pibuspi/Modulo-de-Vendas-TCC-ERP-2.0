package view;

import controller.BalcaoController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Eduardo Yuri: PDV com busca, tabela de produtos, carrinho e acesso ao pagamento. */
public class TelaBalcaoView extends JPanel {
    private final BalcaoController controller = new BalcaoController();
    private final JTextField busca = new JTextField(18);
    private final JTextField codigo = new JTextField(8);
    private final JTextField quantidade = new JTextField("1", 5);
    private final JLabel total = new JLabel("Total: R$ 0,00");
    private final DefaultTableModel produtos = new DefaultTableModel(new Object[]{"Código", "Produto", "Quantidade", "Vendas", "Preço"}, 0);
    public TelaBalcaoView() {
        setLayout(new BorderLayout(8, 8)); setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT)); topo.add(new JLabel("Pesquisar:")); topo.add(busca);
        JButton pesquisar = new JButton("Buscar"); pesquisar.addActionListener(e -> carregarProdutos()); topo.add(pesquisar); add(topo, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(produtos)), BorderLayout.CENTER);
        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT)); acoes.add(new JLabel("Código:")); acoes.add(codigo); acoes.add(new JLabel("Quantidade:")); acoes.add(quantidade);
        JButton incluir = new JButton("Incluir no carrinho"); incluir.addActionListener(e -> incluir()); acoes.add(incluir);
        JButton remover = new JButton("Remover por código"); remover.addActionListener(e -> { controller.remover(codigo.getText()); atualizarTotal(); }); acoes.add(remover);
        JButton pagamento = new JButton("Ir para pagamento"); pagamento.addActionListener(e -> abrirPagamento()); acoes.add(pagamento); acoes.add(total); add(acoes, BorderLayout.SOUTH); carregarProdutos();
    }
    private void carregarProdutos() { produtos.setRowCount(0); controller.buscar(busca.getText()).forEach(p -> produtos.addRow(new Object[]{p.getCodigo(), p.getProduto(), p.getQuantidade(), p.getVendas(), p.getPreco()})); }
    private void incluir() { try { controller.adicionar(codigo.getText(), Double.parseDouble(quantidade.getText())); atualizarTotal(); } catch (RuntimeException ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE); } }
    private void atualizarTotal() { total.setText(String.format("Total: R$ %.2f", controller.calcularTotal())); }
    private void abrirPagamento() { JFrame janela = new JFrame("Tela de Pagamento"); janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); janela.add(new TelaPagamentoView(controller.calcularTotal())); janela.pack(); janela.setLocationRelativeTo(this); janela.setVisible(true); }
}
