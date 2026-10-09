package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/** Eúde: formulário em três abas (Dados Cadastrais, Regras Comerciais e Configuração Fiscal). */
public class CadastroClienteView extends JPanel {
        // Listas provisórias (wireframe). TODO-INTEGRACAO: trocar pela fonte oficial do Back.
    private static final String[] STATUS = {"PENDENTE", "ATIVO", "BLOQUEADO"};
    private static final String[] CONDICOES = {"Selecione", "À Vista", "7 dias", "15 dias", "30 dias",
        "30/60 dias", "30/60/90 dias", "60/90 dias"};
    private static final String[] TABELAS = {"Selecione", "Ouro", "Prata", "Bronze", "Varejo", "Atacado"};
    private static final String[] ROTAS = {"Rota A2 (Sudeste)", "Rota B1 (Sul)", "Rota C3 (Nordeste)"};
    private static final String[] PRAZOS = {"1 dia útil", "2 dias úteis", "3 dias úteis", "5 dias úteis",
        "7 dias úteis", "10 dias úteis"};

    // Aba 1
    private final JTextField campoCpfCnpj = new JTextField(20);
    private final JComboBox<String> campoStatus = new JComboBox<>(STATUS);
    private final JTextField campoRazaoSocial = new JTextField(30);
    private final JTextField campoNomeFantasia = new JTextField(30);
    private final JTextField campoInscricaoEstadual = new JTextField(20);
    private final JTextField campoInscricaoMunicipal = new JTextField(20);
  

    // Aba 2
    private final JComboBox<String> campoCondicao = new JComboBox<>(CONDICOES);
    private final JTextField campoLimiteCredito = new JTextField("0", 15);
    private final JCheckBox campoBloqueioInadimplencia = new JCheckBox("Bloquear novos pedidos por inadimplência");
    private final JComboBox<String> campoTabelaPreco = new JComboBox<>(TABELAS);
    private final JTextField campoAlcadaDesconto = new JTextField("0", 8);
    private final JLabel rotuloAlcada = new JLabel(" ");
    private final JComboBox<String> campoRotaEntrega = new JComboBox<>(ROTAS);
    private final JComboBox<String> campoPrazoMedio = new JComboBox<>(PRAZOS);

    // Aba 3
    private DefaultTableModel modeloRegrasFiscais;
    private JTable tabelaRegrasFiscais;
    private JLabel alertaHardStop;


    public CadastroClienteView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setPreferredSize(new Dimension(900, 600));

        JLabel titulo = new JLabel("Cadastro de Cliente");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        add(titulo, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Dados Cadastrais", new JScrollPane(criarAbaDadosCadastrais()));
        abas.addTab("Regras Comerciais", new JScrollPane(criarAbaRegrasComerciais()));
        abas.addTab("Configuração Fiscal", criarAbaConfiguracaoFiscal());
        add(abas, BorderLayout.CENTER);

        add(criarBotoes(), BorderLayout.SOUTH);
    }

    // Aba 1: Dados Cadastrais
    private JPanel criarAbaDadosCadastrais() {
        JPanel aba = new JPanel(new GridBagLayout());
        aba.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        Formulario.adicionarLinha(aba, 0, "CPF/CNPJ *", campoCpfCnpj);
        Formulario.adicionarLinha(aba, 1, "Status *", campoStatus);
        Formulario.adicionarLinha(aba, 2, "Razão Social *", campoRazaoSocial);
        Formulario.adicionarLinha(aba, 3, "Nome Fantasia", campoNomeFantasia);
        Formulario.adicionarLinha(aba, 4, "Inscrição Estadual", campoInscricaoEstadual);
        Formulario.adicionarLinha(aba, 5, "Inscrição Municipal", campoInscricaoMunicipal);
        Formulario.adicionarLinha(aba, 6, "Endereços de Entrega", new JLabel("Gerenciamento de endereços em desenvolvimento."));
        Formulario.fecharFormulario(aba, 7);
        return aba;
    }

    // Aba 2: Regras Comerciais
    private JPanel criarAbaRegrasComerciais() {
        JPanel aba = new JPanel(new GridBagLayout());
        aba.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        Formulario.adicionarLinha(aba, 0, "Condição de Pagamento *", campoCondicao);
        Formulario.adicionarLinha(aba, 1, "Limite de Crédito (R$)", campoLimiteCredito);
        Formulario.adicionarLinha(aba, 2, "Bloqueio por Inadimplência", campoBloqueioInadimplencia);
        Formulario.adicionarLinha(aba, 3, "Tabela de Preços", campoTabelaPreco);
        Formulario.adicionarLinha(aba, 4, "Alçada Máxima de Desconto (%)", campoAlcadaDesconto);
        Formulario.adicionarLinha(aba, 5, "", rotuloAlcada);
        Formulario.adicionarLinha(aba, 6, "Rota de Entrega", campoRotaEntrega);
        Formulario.adicionarLinha(aba, 7, "Prazo Médio (Lead Time)", campoPrazoMedio);
        Formulario.fecharFormulario(aba, 8);
        return aba;
    }

    // Aba 3: Configuração Fiscal
    private JPanel criarAbaConfiguracaoFiscal() {
    JPanel aba = new JPanel(new BorderLayout(8, 8));
    aba.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

    JPanel cabecalho = new JPanel(new BorderLayout());

    JLabel titulo = new JLabel("Configuração Fiscal");
    titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 15f));

    JLabel descricao = new JLabel(
            "Parametrização de regras por Estabelecimento, UF e Operação."
    );

    JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
    textos.add(titulo);
    textos.add(descricao);

    cabecalho.add(textos, BorderLayout.WEST);
    aba.add(cabecalho, BorderLayout.NORTH);

    JPanel centro = new JPanel(new BorderLayout(8, 8));

    alertaHardStop = new JLabel();
    alertaHardStop.setBorder(
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
    );

    centro.add(alertaHardStop, BorderLayout.NORTH);

    String[] colunas = {
        "Estabelecimento",
        "UF Destino",
        "Operação",
        "CFOP",
        "Preview de Impostos",
        "Status"
    };

    modeloRegrasFiscais = new DefaultTableModel(colunas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    tabelaRegrasFiscais = new JTable(modeloRegrasFiscais);
    tabelaRegrasFiscais.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
    );
    tabelaRegrasFiscais.setRowHeight(24);
    tabelaRegrasFiscais.getTableHeader().setReorderingAllowed(false);

    centro.add(
            new JScrollPane(tabelaRegrasFiscais),
            BorderLayout.CENTER
    );

    aba.add(centro, BorderLayout.CENTER);

    JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 8, 0)
    );

    JButton botaoNovaRegra = new JButton("Nova Regra");
    JButton botaoEditar = new JButton("Editar");
    JButton botaoTestar = new JButton("Testar");
    JButton botaoRemover = new JButton("Remover");

    botaoNovaRegra.addActionListener(
            e -> abrirDialogoRegraFiscal(-1)
    );

    botaoEditar.addActionListener(
            e -> editarRegraFiscal()
    );

    botaoTestar.addActionListener(
            e -> testarRegraFiscal()
    );

    botaoRemover.addActionListener(
            e -> removerRegraFiscal()
    );

    botoes.add(botaoNovaRegra);
    botoes.add(botaoEditar);
    botoes.add(botaoTestar);
    botoes.add(botaoRemover);

    aba.add(botoes, BorderLayout.SOUTH);

    carregarRegrasFiscaisExemplo();

    return aba;
}


    private JPanel criarBotoes() {
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton botaoCancelar = new JButton("Cancelar");
        JButton botaoSalvar = new JButton("Salvar");
        botaoCancelar.addActionListener(e -> limpar());
        botaoSalvar.addActionListener(e -> salvar());
        botoes.add(botaoCancelar);
        botoes.add(botaoSalvar);
        return botoes;
    }


    /** Retorna a mensagem de erro, ou null se o formulário estiver válido. */
    private String validarFormulario() {
        String documento = campoCpfCnpj.getText().replaceAll("\\D", "");
        if (documento.length() != 11 && documento.length() != 14) {
            return "CPF/CNPJ deve ter 11 ou 14 dígitos.";
        }
        if (campoRazaoSocial.getText().isBlank()) {
            return "Razão Social é obrigatória.";
        }
       
        if (campoCondicao.getSelectedIndex() == 0) {
            return "Selecione a Condição de Pagamento.";
        }
        try {
            if (Formato.lerNumero(campoLimiteCredito.getText()) < 0) {
                return "Limite de Crédito não pode ser negativo.";
            }
        } catch (IllegalArgumentException e) {
            return "Limite de Crédito inválido. Use o formato 1000,00.";
        }
        try {
            double alcada = Formato.lerNumero(campoAlcadaDesconto.getText());
            if (alcada < 0 || alcada > 100) {
                           return "Alçada de desconto deve estar entre 0 e 100.";
            }
        } catch (IllegalArgumentException e) {
            return "Alçada de desconto inválida. Use o formato 5,00.";
        }
        if ("ATIVO".equals(campoStatus.getSelectedItem()) && campoTabelaPreco.getSelectedIndex() == 0) {
            return "Tabela de Preços é obrigatória para ativar o cliente.";
        }
        return null;
    }


    /** Aviso visual da alçada. TODO-INTEGRACAO: o texto oficial deve vir do Controller. */
    private void atualizarAvisoAlcada() {
        try {
            double alcada = Formato.lerNumero(campoAlcadaDesconto.getText());
            if (alcada <= 5) {
                rotuloAlcada.setText("Dentro da alçada padrão (até 5%).");
            } else if (alcada <= 10) {
                rotuloAlcada.setText("Requer aprovação do Gerente Comercial.");
            } else {
                rotuloAlcada.setText("Requer senha de supervisor.");
            }
        } catch (IllegalArgumentException e) {
            rotuloAlcada.setText(" ");
        }
    }


    private void salvar() {
        atualizarAvisoAlcada();
        String erro = validarFormulario();
        if (erro != null) {
            JOptionPane.showMessageDialog(this, erro, "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // TODO-INTEGRACAO: montar o Cliente e chamar o ClienteController quando existir.
        JOptionPane.showMessageDialog(this, "Dados validados e prontos para envio.",
                "Cadastro de Cliente", JOptionPane.INFORMATION_MESSAGE);
    }

    
    private void limpar() {
        campoCpfCnpj.setText("");
        campoStatus.setSelectedIndex(0);
        campoRazaoSocial.setText("");
        campoNomeFantasia.setText("");
        campoInscricaoEstadual.setText("");
        campoInscricaoMunicipal.setText("");
        campoCondicao.setSelectedIndex(0);
        campoLimiteCredito.setText("0");
        campoBloqueioInadimplencia.setSelected(false);
        campoTabelaPreco.setSelectedIndex(0);
        campoAlcadaDesconto.setText("0");
        rotuloAlcada.setText(" ");
        campoRotaEntrega.setSelectedIndex(0);
        campoPrazoMedio.setSelectedIndex(0);
    }

    private void carregarRegrasFiscaisExemplo() {
    modeloRegrasFiscais.setRowCount(0);

    modeloRegrasFiscais.addRow(new Object[]{
        "Matriz SP",
        "SP",
        "Venda de Mercadoria",
        "5.102",
        "ICMS 12%, IPI 5%, PIS 1,65%, COFINS 7,6%",
        "CONFIGURADO"
    });

    modeloRegrasFiscais.addRow(new Object[]{
        "Matriz SP",
        "RJ",
        "Venda de Mercadoria",
        "6.102",
        "ICMS 12%, IPI 5%, PIS 1,65%, COFINS 7,6%",
        "CONFIGURADO"
    });

    modeloRegrasFiscais.addRow(new Object[]{
        "Filial RJ",
        "MG",
        "Venda de Mercadoria",
        "6.102",
        "ICMS 12%, IPI 5%, PIS 1,65%, COFINS 7,6%",
        "CONFIGURADO"
    });

    modeloRegrasFiscais.addRow(new Object[]{
        "Matriz SP",
        "RS",
        "Venda de Mercadoria",
        "",
        "Não definido",
        "PENDENTE"
    });

    modeloRegrasFiscais.addRow(new Object[]{
        "Matriz SP",
        "BA",
        "Venda de Mercadoria",
        "",
        "Não definido",
        "PENDENTE"
    });

    atualizarAlertaHardStop();
}

private void atualizarAlertaHardStop() {
    int pendentes = 0;

    StringBuilder ufs = new StringBuilder();

    for (int i = 0; i < modeloRegrasFiscais.getRowCount(); i++) {
        String status = String.valueOf(
                modeloRegrasFiscais.getValueAt(i, 5)
        );

        if ("PENDENTE".equalsIgnoreCase(status)) {
            pendentes++;

            String uf = String.valueOf(
                    modeloRegrasFiscais.getValueAt(i, 1)
            );

            if (ufs.length() > 0) {
                ufs.append(", ");
            }

            ufs.append(uf);
        }
    }

    if (pendentes == 0) {
        alertaHardStop.setVisible(false);
    } else {
        alertaHardStop.setText(
                "<html><b>Hard Stop Ativo:</b> "
                + pendentes
                + " regra(s) fiscal(is) pendente(s). "
                + "UF(s) afetada(s): "
                + ufs
                + ".</html>"
        );

        alertaHardStop.setVisible(true);
    }
}

private void abrirDialogoRegraFiscal(int linhaEdicao) {
    boolean editando = linhaEdicao >= 0;

    JDialog dialogo = new JDialog(
            JOptionPane.getFrameForComponent(this),
            editando ? "Editar Regra Fiscal" : "Nova Regra Fiscal",
            true
    );

    dialogo.setLayout(new BorderLayout(10, 10));

    JPanel formulario = new JPanel(new GridBagLayout());
    formulario.setBorder(
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
    );

    JComboBox<String> estabelecimento = new JComboBox<>(
            new String[]{"Matriz SP", "Filial RJ"}
    );

    JComboBox<String> uf = new JComboBox<>(
            new String[]{"SP", "RJ", "MG", "RS", "BA"}
    );

    JComboBox<String> operacao = new JComboBox<>(
            new String[]{
                "Venda de Mercadoria",
                "Bonificação",
                "Amostra Grátis"
            }
    );

    JComboBox<String> cfop = new JComboBox<>(
            new String[]{
                "",
                "5.102",
                "5.911",
                "6.102",
                "6.910"
            }
    );

    JLabel preview = new JLabel(
            "ICMS 12%, IPI 5%, PIS 1,65%, COFINS 7,6%"
    );

    if (editando) {
        estabelecimento.setSelectedItem(
                modeloRegrasFiscais.getValueAt(linhaEdicao, 0)
        );

        uf.setSelectedItem(
                modeloRegrasFiscais.getValueAt(linhaEdicao, 1)
        );

        operacao.setSelectedItem(
                modeloRegrasFiscais.getValueAt(linhaEdicao, 2)
        );

        cfop.setSelectedItem(
                modeloRegrasFiscais.getValueAt(linhaEdicao, 3)
        );
    }

    Formulario.adicionarLinha(
            formulario, 0, "Estabelecimento *", estabelecimento
    );

    Formulario.adicionarLinha(
            formulario, 1, "UF de Destino *", uf
    );

    Formulario.adicionarLinha(
            formulario, 2, "Operação Comercial *", operacao
    );

    Formulario.adicionarLinha(
            formulario, 3, "CFOP *", cfop
    );

    Formulario.adicionarLinha(
            formulario, 4, "Preview de Impostos", preview
    );

    Formulario.fecharFormulario(formulario, 5);

    cfop.addActionListener(e -> {
        String valor = String.valueOf(cfop.getSelectedItem());

        if (valor.isBlank()) {
            preview.setText("Não definido");
        } else {
            preview.setText(
                    "ICMS 12%, IPI 5%, PIS 1,65%, COFINS 7,6%"
            );
        }
    });

    JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 8, 8)
    );

    JButton cancelar = new JButton("Cancelar");
    JButton salvar = new JButton("Salvar Regra");

    cancelar.addActionListener(
            e -> dialogo.dispose()
    );

    salvar.addActionListener(e -> {
        String valorCfop = String.valueOf(
                cfop.getSelectedItem()
        );

        if (valorCfop.isBlank()) {
            JOptionPane.showMessageDialog(
                    dialogo,
                    "Informe o CFOP para salvar a regra.",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Object[] regra = {
            estabelecimento.getSelectedItem(),
            uf.getSelectedItem(),
            operacao.getSelectedItem(),
            valorCfop,
            "ICMS 12%, IPI 5%, PIS 1,65%, COFINS 7,6%",
            "CONFIGURADO"
        };

        if (editando) {
            for (int i = 0; i < regra.length; i++) {
                modeloRegrasFiscais.setValueAt(
                        regra[i], linhaEdicao, i
                );
            }
        } else {
            modeloRegrasFiscais.addRow(regra);
        }

        atualizarAlertaHardStop();
        dialogo.dispose();
    });

    botoes.add(cancelar);
    botoes.add(salvar);

    dialogo.add(formulario, BorderLayout.CENTER);
    dialogo.add(new JSeparator(), BorderLayout.SOUTH);

    JPanel painelBotoes = new JPanel(
            new BorderLayout()
    );

    painelBotoes.add(
            botoes,
            BorderLayout.EAST
    );

    dialogo.add(
            painelBotoes,
            BorderLayout.SOUTH
    );

    dialogo.setSize(560, 350);
    dialogo.setLocationRelativeTo(this);
    dialogo.setResizable(false);
    dialogo.setVisible(true);
}

private void editarRegraFiscal() {
    int linha = tabelaRegrasFiscais.getSelectedRow();

    if (linha < 0) {
        JOptionPane.showMessageDialog(
                this,
                "Selecione uma regra fiscal para editar.",
                "Editar Regra",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    abrirDialogoRegraFiscal(linha);
}

private void testarRegraFiscal() {
    int linha = tabelaRegrasFiscais.getSelectedRow();

    if (linha < 0) {
        JOptionPane.showMessageDialog(
                this,
                "Selecione uma regra fiscal para testar.",
                "Testar Regra",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String status = String.valueOf(
            modeloRegrasFiscais.getValueAt(linha, 5)
    );

    String estabelecimento = String.valueOf(
            modeloRegrasFiscais.getValueAt(linha, 0)
    );

    String uf = String.valueOf(
            modeloRegrasFiscais.getValueAt(linha, 1)
    );

    String operacao = String.valueOf(
            modeloRegrasFiscais.getValueAt(linha, 2)
    );

    if ("PENDENTE".equalsIgnoreCase(status)) {
        JOptionPane.showMessageDialog(
                this,
                "Regra fiscal pendente.\n\n"
                + "Estabelecimento: " + estabelecimento + "\n"
                + "UF: " + uf + "\n"
                + "Operação: " + operacao + "\n\n"
                + "A combinação não possui CFOP configurado.",
                "Hard Stop",
                JOptionPane.WARNING_MESSAGE
        );
    } else {
        JOptionPane.showMessageDialog(
                this,
                "Regra fiscal configurada.\n\n"
                + "Estabelecimento: " + estabelecimento + "\n"
                + "UF: " + uf + "\n"
                + "Operação: " + operacao,
                "Teste da Regra",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}

private void removerRegraFiscal() {
    int linha = tabelaRegrasFiscais.getSelectedRow();

    if (linha < 0) {
        JOptionPane.showMessageDialog(
                this,
                "Selecione uma regra fiscal para remover.",
                "Remover Regra",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int resposta = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente remover esta regra?",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (resposta == JOptionPane.YES_OPTION) {
        modeloRegrasFiscais.removeRow(linha);
        atualizarAlertaHardStop();
    }
}
}
