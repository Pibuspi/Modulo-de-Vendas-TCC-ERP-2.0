package view;

import controller.ConsultaVendasController;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Matheus Godoy: interface de acompanhamento do ciclo de vendas.
 * A view apresenta filtros, tabela de pedidos e cards de resumo/KPI.
 */
public class ConsultaHistoricoVendasView extends JPanel {
    private final ConsultaVendasController controller;
    private final JTextField campoDataInicial = new JTextField(10);
    private final JTextField campoDataFinal = new JTextField(10);
    private final JTextField campoCliente = new JTextField(15);
    private final JTextField campoVendedor = new JTextField(15);
    private final JComboBox<String> campoStatus = new JComboBox<>(new String[]{
        "TODOS", "ABERTO", "BLOQUEADO", "PENDENTE_APROVACAO",
        "LIBERADO_PARA_FATURAMENTO", "DEVOLVIDO_AO_VENDEDOR"
    });
    private final JLabel totalVendido = new JLabel("R$ 0,00");
    private final JLabel pedidosAbertos = new JLabel("0");
    private final JLabel pedidosBloqueados = new JLabel("0");

    public ConsultaHistoricoVendasView() {
        this(new ConsultaVendasController());
    }

    public ConsultaHistoricoVendasView(ConsultaVendasController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(criarPainelFiltros(), BorderLayout.NORTH);
        add(criarTabelaPedidos(), BorderLayout.CENTER);
        add(criarPainelKpis(), BorderLayout.SOUTH);
    }

    /** Cria os filtros de período, cliente, vendedor e status do pedido. */
    private JPanel criarPainelFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros de consulta"));
        filtros.add(new JLabel("De:")); filtros.add(campoDataInicial);
        filtros.add(new JLabel("Até:")); filtros.add(campoDataFinal);
        filtros.add(new JLabel("Cliente:")); filtros.add(campoCliente);
        filtros.add(new JLabel("Vendedor:")); filtros.add(campoVendedor);
        filtros.add(new JLabel("Status:")); filtros.add(campoStatus);
        JButton pesquisar = new JButton("Pesquisar");
        pesquisar.addActionListener(e -> atualizarConsulta());
        filtros.add(pesquisar);
        return filtros;
    }

    /** Cria a JTable que exibirá pedidos, itens e valores totais. */
    private JScrollPane criarTabelaPedidos() {
        String[] colunas = {"Pedido", "Cliente", "Vendedor", "Status", "Itens", "Total"};
        JTable tabela = new JTable(new Object[0][colunas.length], colunas);
        tabela.setAutoCreateRowSorter(true);
        return new JScrollPane(tabela);
    }

    /** Cria os cards de total vendido, pedidos abertos e pedidos bloqueados. */
    private JPanel criarPainelKpis() {
        JPanel kpis = new JPanel(new GridLayout(1, 3, 10, 0));
        kpis.add(criarCard("Total vendido no período", totalVendido));
        kpis.add(criarCard("Pedidos em aberto", pedidosAbertos));
        kpis.add(criarCard("Pedidos bloqueados", pedidosBloqueados));
        return kpis;
    }

    private JPanel criarCard(String titulo, JLabel valor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder(titulo));
        valor.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valor, BorderLayout.CENTER);
        return card;
    }

    /** Consulta o controller e atualiza tabela e indicadores após a ação de pesquisa. */
    private void atualizarConsulta() {
        LocalDate inicio = campoDataInicial.getText().isBlank() ? null : LocalDate.parse(campoDataInicial.getText());
        LocalDate fim = campoDataFinal.getText().isBlank() ? null : LocalDate.parse(campoDataFinal.getText());
        var pedidos = controller.consultar(inicio, fim, campoCliente.getText(), campoVendedor.getText(),
            String.valueOf(campoStatus.getSelectedItem()));
        totalVendido.setText(String.format("R$ %.2f", controller.calcularTotalVendido(pedidos)));
        pedidosAbertos.setText(String.valueOf(controller.contarPedidosAbertos(pedidos)));
        pedidosBloqueados.setText(String.valueOf(controller.contarPedidosBloqueados(pedidos)));
    }
}
