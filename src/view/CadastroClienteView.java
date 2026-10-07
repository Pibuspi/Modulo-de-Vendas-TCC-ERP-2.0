package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
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
        JPanel aba = new JPanel(new BorderLayout());
        aba.add(new JLabel("Configuração Fiscal em desenvolvimento.", SwingConstants.CENTER), BorderLayout.CENTER);
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
}
