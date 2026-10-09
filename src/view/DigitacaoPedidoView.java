package view;

import controller.PedidoController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.ItemPedido;
import model.PedidoVenda;
import model.Produto;

/** Eúde: digitação de pedidos (cliente, itens, totais e ações). */
public class DigitacaoPedidoView extends JPanel {

    private final PedidoController controller = new PedidoController();
    private PedidoVenda pedido = new PedidoVenda();

    private final JTextField campoCpfCnpj = new JTextField(16);
    private final JTextField campoRazaoSocial = new JTextField(24);
    private final JTextField campoCodigo = new JTextField(8);
    private final JTextField campoProduto = new JTextField(16);
    private final JTextField campoQuantidade = new JTextField(5);
    private final JTextField campoPreco = new JTextField(8);
    private final JTextField campoDesconto = new JTextField("0", 8);

    private final DefaultTableModel modeloItens = new DefaultTableModel(
            new Object[]{"Código", "Produto", "Qtd", "Preço Unit.", "Desconto", "Subtotal"}, 0) {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };
    private final JTable tabelaItens = new JTable(modeloItens);
    private final JLabel rotuloTotal = new JLabel();

    public DigitacaoPedidoView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setPreferredSize(new Dimension(900, 600));

        JLabel titulo = new JLabel("Digitação de Pedido");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        JPanel topo = new JPanel();
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        topo.add(titulo);
        topo.add(criarPainelCliente());
        topo.add(criarPainelItem());

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);

        recarregarTabela();
    }

    private JPanel criarPainelCliente() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBorder(BorderFactory.createTitledBorder("Cliente"));
        painel.add(new JLabel("CPF/CNPJ:"));
        painel.add(campoCpfCnpj);
        painel.add(new JLabel("Razão Social:"));
        painel.add(campoRazaoSocial);
        return painel;
    }

    private JPanel criarPainelItem() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBorder(BorderFactory.createTitledBorder("Novo item"));
        painel.add(new JLabel("Código:"));
        painel.add(campoCodigo);
        painel.add(new JLabel("Produto:"));
        painel.add(campoProduto);
        painel.add(new JLabel("Qtd:"));
        painel.add(campoQuantidade);
        painel.add(new JLabel("Preço:"));
        painel.add(campoPreco);
        painel.add(new JLabel("Desconto:"));
        painel.add(campoDesconto);
        JButton botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.addActionListener(e -> adicionarItem());
        painel.add(botaoAdicionar);
        return painel;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout());
        rotuloTotal.setFont(rotuloTotal.getFont().deriveFont(Font.BOLD, 16f));
        rodape.add(rotuloTotal, BorderLayout.WEST);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton botaoRemover = new JButton("Remover item");
        JButton botaoLimpar = new JButton("Limpar");
        JButton botaoSalvar = new JButton("Salvar");
        botaoRemover.addActionListener(e -> removerItem());
        botaoLimpar.addActionListener(e -> limpar());
        botaoSalvar.addActionListener(e -> salvar());
        botoes.add(botaoRemover);
        botoes.add(botaoLimpar);
        botoes.add(botaoSalvar);
        rodape.add(botoes, BorderLayout.EAST);
        return rodape;
    }

    private void adicionarItem() {
        try {
            String codigo = campoCodigo.getText().trim();
            String descricao = campoProduto.getText().trim();
            if (codigo.isEmpty() || descricao.isEmpty()) {
                throw new IllegalArgumentException("Informe o código e o produto.");
            }
            double quantidade = Formato.lerNumero(campoQuantidade.getText());
            double preco = Formato.lerNumero(campoPreco.getText());
            double desconto = campoDesconto.getText().isBlank() ? 0 : Formato.lerNumero(campoDesconto.getText());
            ItemPedido item = new ItemPedido();
            item.setProduto(new Produto(codigo, descricao, 0, 0, preco));
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(preco);
            item.setDesconto(desconto);

            controller.adicionarItem(pedido, item);
            recarregarTabela();
            limparCamposItem();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Pedido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerItem() {
        int linhaVisual = tabelaItens.getSelectedRow();
        if (linhaVisual < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover.",
                    "Pedido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int linha = tabelaItens.convertRowIndexToModel(linhaVisual);
        controller.removerItem(pedido, pedido.getItens().get(linha));
        recarregarTabela();
    }

     /** Reconstrói a tabela a partir do pedido, que é a fonte única dos dados. */
    private void recarregarTabela() {
        modeloItens.setRowCount(0);
        for (ItemPedido item : pedido.getItens()) {
            modeloItens.addRow(new Object[]{
                item.getProduto().getCodigo(),
                item.getProduto().getProduto(),
                Formato.numero(item.getQuantidade()),
                Formato.moeda(item.getPrecoUnitario()),
                Formato.moeda(item.getDesconto()),
                Formato.moeda(item.getSubtotal())
            });
        }
        rotuloTotal.setText("Total: " + Formato.moeda(controller.calcularTotal(pedido)));
    }

    private void salvar() {
        if (campoCpfCnpj.getText().isBlank() || campoRazaoSocial.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF/CNPJ e a Razão Social do cliente.",
                    "Pedido", JOptionPane.WARNING_MESSAGE);
                    return;
        }
        // TODO-INTEGRACAO: buscar o cliente pelo ClienteController/DAO em vez de montar aqui.
        Cliente cliente = new Cliente();
        cliente.setCpfCnpj(campoCpfCnpj.getText().trim());
        cliente.setRazaoSocial(campoRazaoSocial.getText().trim());
        pedido.setCliente(cliente);

        if (!controller.validar(pedido)) {
            JOptionPane.showMessageDialog(this,
                    "Pedido inválido: informe o cliente e ao menos um item com subtotal não negativo.",
                    "Pedido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // TODO-INTEGRACAO: enviar o pedido ao Controller/DAO quando existirem.
        JOptionPane.showMessageDialog(this,
                "Pedido pronto para envio. " + rotuloTotal.getText(),
                "Pedido", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpar() {
        pedido = new PedidoVenda();
        campoCpfCnpj.setText("");
        campoRazaoSocial.setText("");
        limparCamposItem();
        recarregarTabela();
    }

    private void limparCamposItem() {
        campoCodigo.setText("");
        campoProduto.setText("");
        campoQuantidade.setText("");
        campoPreco.setText("");
        campoDesconto.setText("0");
    }
}
