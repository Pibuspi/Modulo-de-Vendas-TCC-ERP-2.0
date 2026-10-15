package view;

import controller.LiberacaoComercialController;
import model.PedidoVenda;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Cockpit de Liberação e Consolidação Comercial (View pura e limpa).
 * Toda a lógica de processamento e filtragem é delegada ao LiberacaoComercialController.
 */
public class LiberacaoComercialView extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private final LiberacaoComercialController controller;

    private static final String[] STATUS = {"TODOS", "ABERTO", "BLOQUEADO", "PENDENTE_APROVACAO",
        "LIBERADO_PARA_FATURAMENTO", "DEVOLVIDO_AO_VENDEDOR"};
    private static final String[] PAGAMENTOS = {"TODOS", "À Vista", "7 dias", "15 dias", "30 dias",
        "30/60 dias"};
    private static final String[] COLUNAS = {"Pedido", "Cliente", "Vendedor", "Pagamento", "Status", "Total"};

    private static final int COLUNA_CLIENTE = 1;
    private static final int COLUNA_PAGAMENTO = 3;
    private static final int COLUNA_STATUS = 4;
    private static final int COLUNA_TOTAL = 5;

    // Componentes de Filtro
    private final JTextField campoCliente = new JTextField(10);
    private final JTextField campoVendedor = new JTextField(10);
    private final JComboBox<String> campoPagamento = new JComboBox<>(PAGAMENTOS);
    private final JComboBox<String> campoStatus = new JComboBox<>(STATUS);

    // Cards de KPI
    private final JLabel kpiAbertos = new JLabel("0");
    private final JLabel kpiPendentes = new JLabel("0");
    private final JLabel kpiBloqueados = new JLabel("0");
    private final JLabel kpiLiberados = new JLabel("0");

    // Tabela e Modelo
    private final DefaultTableModel modeloPedidos = new DefaultTableModel(COLUNAS, 0) {
        private static final long serialVersionUID = 1L;
        @Override
        public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    private final JTable tabelaPedidos = new JTable(modeloPedidos);

    // Painel Lateral de Consolidação
    private final JLabel rotuloQuantidade = new JLabel("0");
    private final JLabel rotuloClientes = new JLabel("0");
    private final JLabel rotuloPagamentos = new JLabel("0");
    private final JLabel rotuloTotal = new JLabel(Formato.moeda(0));
    private final JLabel alertaIncompatibilidade = new JLabel();
    private final JCheckBox checkCredito = new JCheckBox("Crédito conferido");
    private final JCheckBox checkMargem = new JCheckBox("Margem conferida");
    private final JCheckBox checkAlcada = new JCheckBox("Alçada conferida");

    public LiberacaoComercialView() {
        this.controller = new LiberacaoComercialController();
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setPreferredSize(new Dimension(900, 600));

        add(criarTopo(), BorderLayout.NORTH);
        add(criarCorpo(), BorderLayout.CENTER);
        add(criarBotoes(), BorderLayout.SOUTH);

        configurarEventosFiltros();
        atualizarTela();
    }

    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout(0, 10));
        JLabel titulo = new JLabel("Liberação e Consolidação Comercial");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        topo.add(titulo, BorderLayout.NORTH);
        topo.add(criarKpis(), BorderLayout.CENTER);
        topo.add(criarFiltros(), BorderLayout.SOUTH);
        return topo;
    }

    private JPanel criarKpis() {
        JPanel kpis = new JPanel(new GridLayout(1, 4, 10, 0));
        kpis.add(criarCard("Em aberto", kpiAbertos));
        kpis.add(criarCard("Pendentes de aprovação", kpiPendentes));
        kpis.add(criarCard("Bloqueados", kpiBloqueados));
        kpis.add(criarCard("Liberados para faturamento", kpiLiberados));
        return kpis;
    }

    private JPanel criarCard(String titulo, JLabel valor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder(titulo));
        valor.setFont(valor.getFont().deriveFont(Font.BOLD, 22f));
        valor.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valor, BorderLayout.CENTER);
        return card;
    }

    private JPanel criarFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        filtros.add(new JLabel("Cliente:"));
        filtros.add(campoCliente);
        filtros.add(new JLabel("Vendedor:"));
        filtros.add(campoVendedor);
        filtros.add(new JLabel("Pagamento:"));
        filtros.add(campoPagamento);
        filtros.add(new JLabel("Status:"));
        filtros.add(campoStatus);
        return filtros;
    }

    private JPanel criarCorpo() {
        JPanel corpo = new JPanel(new BorderLayout(10, 0));
        corpo.add(criarTabela(), BorderLayout.CENTER);
        corpo.add(criarPainelConsolidacao(), BorderLayout.EAST);
        return corpo;
    }

    private JPanel criarTabela() {
        tabelaPedidos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tabelaPedidos.setRowHeight(24);
        tabelaPedidos.getTableHeader().setReorderingAllowed(false);
        tabelaPedidos.getColumnModel().getColumn(COLUNA_TOTAL).setCellRenderer(new RenderizadorMoeda());
        
        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarConsolidacao();
            }
        });

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Pedidos"));
        painel.add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelConsolidacao() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Consolidação"));
        painel.setPreferredSize(new Dimension(220, 0));

        JPanel resumo = new JPanel(new GridBagLayout());
        Formulario.adicionarLinha(resumo, 0, "Pedidos", rotuloQuantidade);
        Formulario.adicionarLinha(resumo, 1, "Clientes", rotuloClientes);
        Formulario.adicionarLinha(resumo, 2, "Pagamentos", rotuloPagamentos);
        Formulario.adicionarLinha(resumo, 3, "Total", rotuloTotal);

        JPanel checklist = new JPanel(new GridLayout(0, 1));
        checklist.setBorder(BorderFactory.createTitledBorder("Checklist"));
        checklist.add(checkCredito);
        checklist.add(checkMargem);
        checklist.add(checkAlcada);

        alertaIncompatibilidade.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        alertaIncompatibilidade.setVisible(false);

        JPanel inferior = new JPanel(new BorderLayout(0, 10));
        inferior.add(checklist, BorderLayout.NORTH);
        inferior.add(alertaIncompatibilidade, BorderLayout.CENTER);

        painel.add(resumo, BorderLayout.NORTH);
        painel.add(inferior, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarBotoes() {
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton botaoLimpar = new JButton("Limpar Filtros");
        JButton botaoDevolver = new JButton("Devolver ao Vendedor");
        JButton botaoLiberar = new JButton("Liberar");
        
        botaoLimpar.addActionListener(e -> limparFiltros());
        botaoDevolver.addActionListener(e -> devolver());
        botaoLiberar.addActionListener(e -> liberar());
        
        botoes.add(botaoLimpar);
        botoes.add(botaoDevolver);
        botoes.add(botaoLiberar);
        return botoes;
    }

    private void configurarEventosFiltros() {
        campoStatus.addActionListener(e -> atualizarTela());
        campoPagamento.addActionListener(e -> atualizarTela());

        DocumentListener listenerTexto = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { atualizarTela(); }
            @Override public void removeUpdate(DocumentEvent e) { atualizarTela(); }
            @Override public void changedUpdate(DocumentEvent e) { atualizarTela(); }
        };

        campoCliente.getDocument().addDocumentListener(listenerTexto);
        campoVendedor.getDocument().addDocumentListener(listenerTexto);
    }

    private void atualizarTela() {
        String cli = campoCliente.getText();
        String vend = campoVendedor.getText();
        String pag = (String) campoPagamento.getSelectedItem();
        String st = (String) campoStatus.getSelectedItem();

        List<PedidoVenda> filtrados = controller.filtrarPedidos(cli, vend, pag, st);
        
        modeloPedidos.setRowCount(0);
        for (PedidoVenda p : filtrados) {
            Object[] linha = {
                String.valueOf(p.getId()),
                "Comercial Alfa Ltda",
                "Marcos",
                "30 dias",
                String.valueOf(p.getStatus()),
                12500.00
            };
            modeloPedidos.addRow(linha);
        }
        atualizarKpisGerais();
    }

    private void atualizarKpisGerais() {
        List<PedidoVenda> todos = controller.getTodosPedidos();
        int abertos = 0, pendentes = 0, bloqueados = 0, liberados = 0;
        
        for (PedidoVenda p : todos) {
            String st = String.valueOf(p.getStatus());
            if ("ABERTO".equals(st)) abertos++;
            else if ("PENDENTE_APROVACAO".equals(st)) pendentes++;
            else if ("BLOQUEADO".equals(st)) bloqueados++;
            else if ("LIBERADO_PARA_FATURAMENTO".equals(st)) liberados++;
        }

        kpiAbertos.setText(String.valueOf(abertos));
        kpiPendentes.setText(String.valueOf(pendentes));
        kpiBloqueados.setText(String.valueOf(bloqueados));
        kpiLiberados.setText(String.valueOf(liberados));
    }

    private void limparFiltros() {
        campoCliente.setText("");
        campoVendedor.setText("");
        campoPagamento.setSelectedIndex(0);
        campoStatus.setSelectedIndex(0);
        atualizarTela();
    }

    private void liberar() {
        int[] linhas = tabelaPedidos.getSelectedRows();
        if (linhas.length == 0) {
            avisar("Selecione ao menos um pedido para liberar.", "Liberação Comercial");
            return;
        }

        try {
            List<PedidoVenda> selecionados = obterPedidosDasLinhas(linhas);
            controller.validarConsolidacaoLote(selecionados, checkCredito.isSelected(), checkMargem.isSelected(), checkAlcada.isSelected());
            controller.liberarLoteParaFaturamento(selecionados);
            
            JOptionPane.showMessageDialog(this, "Lote liberado e enviado ao faturamento com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparChecklist();
            atualizarTela();
        } catch (Exception ex) {
            avisar(ex.getMessage(), "Validação de Governança");
        }
    }

    private void devolver() {
        int[] linhas = tabelaPedidos.getSelectedRows();
        if (linhas.length == 0) {
            avisar("Selecione ao menos um pedido para devolver.", "Devolver ao Vendedor");
            return;
        }
        abrirDialogoDevolucao(obterPedidosDasLinhas(linhas));
    }

    private void abrirDialogoDevolucao(List<PedidoVenda> selecionados) {
        JDialog dialogo = new JDialog(JOptionPane.getFrameForComponent(this), "Devolver ao Vendedor", true);
        JTextArea txtJustificativa = new JTextArea(5, 30);
        txtJustificativa.setLineWrap(true);

        JButton btnConfirmar = new JButton("Confirmar Devolução");
        btnConfirmar.addActionListener(e -> {
            try {
                controller.devolverAoVendedor(selecionados, txtJustificativa.getText());
                JOptionPane.showMessageDialog(dialogo, "Devolução registrada com sucesso.");
                dialogo.dispose();
                atualizarTela();
            } catch (Exception ex) {
                avisar(ex.getMessage(), "Erro");
            }
        });

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.add(new JLabel("Justificativa obrigatória:"), BorderLayout.NORTH);
        painel.add(new JScrollPane(txtJustificativa), BorderLayout.CENTER);
        painel.add(btnConfirmar, BorderLayout.SOUTH);

        dialogo.add(painel);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private List<PedidoVenda> obterPedidosDasLinhas(int[] linhas) {
        List<PedidoVenda> selecionados = new ArrayList<>();
        List<PedidoVenda> filtrados = controller.filtrarPedidos(campoCliente.getText(), campoVendedor.getText(), (String)campoPagamento.getSelectedItem(), (String)campoStatus.getSelectedItem());
        for (int l : linhas) {
            if (l < filtrados.size()) {
                selecionados.add(filtrados.get(l));
            }
        }
        return selecionados;
    }

    private void atualizarConsolidacao() {
        int[] linhas = tabelaPedidos.getSelectedRows();
        Set<Object> clientes = new HashSet<>();
        Set<Object> pagamentos = new HashSet<>();
        double total = 0;

        for (int linha : linhas) {
            clientes.add(modeloPedidos.getValueAt(linha, COLUNA_CLIENTE));
            pagamentos.add(modeloPedidos.getValueAt(linha, COLUNA_PAGAMENTO));
            total += ((Number) modeloPedidos.getValueAt(linha, COLUNA_TOTAL)).doubleValue();
        }

        rotuloQuantidade.setText(String.valueOf(linhas.length));
        rotuloClientes.setText(String.valueOf(clientes.size()));
        rotuloPagamentos.setText(String.valueOf(pagamentos.size()));
        rotuloTotal.setText(Formato.moeda(total));

        boolean incompativel = clientes.size() > 1 || pagamentos.size() > 1;
        if (incompativel) {
            alertaIncompatibilidade.setText("<html><b>Atenção:</b> clientes ou condições de pagamento divergentes no lote.</html>");
        }
        alertaIncompatibilidade.setVisible(incompativel);
    }

    private void limparChecklist() {
        checkCredito.setSelected(false);
        checkMargem.setSelected(false);
        checkAlcada.setSelected(false);
    }

    private void avisar(String msg, String titulo) {
        JOptionPane.showMessageDialog(this, msg, titulo, JOptionPane.WARNING_MESSAGE);
    }

    private static class RenderizadorMoeda extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;
        RenderizadorMoeda() { setHorizontalAlignment(SwingConstants.RIGHT); }
        @Override protected void setValue(Object valor) {
            setText(valor instanceof Number ? Formato.moeda(((Number) valor).doubleValue()) : "");
        }
    }
}