package view;

import javax.swing.*;
import java.awt.*;

/** Dashboard/launcher central: Eúde mantém a navegação das telas do módulo. */
public class TelaPrincipalView extends JFrame {
    public TelaPrincipalView() {
        setTitle("TCC ERP 2.0 - Módulo de Vendas"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(900, 560); setLocationRelativeTo(null);
        JPanel menu = new JPanel(new GridLayout(0, 2, 10, 10)); menu.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(new JLabel("Módulo de Vendas", SwingConstants.CENTER), BorderLayout.NORTH);
        adicionar(menu, "Cadastro de Clientes", () -> abrir(new CadastroClienteView(), "Cadastro de Clientes"));
        adicionar(menu, "Digitação de Pedidos", () -> abrir(new DigitacaoPedidoView(), "Digitação de Pedidos"));
        adicionar(menu, "Motor de Preços / Alçadas", () -> abrir(new PricingEngineView(), "Pricing Engine"));
        adicionar(menu, "Liberação Comercial", () -> abrir(new LiberacaoComercialView(), "Liberação Comercial"));
        adicionar(menu, "Gestão de Devoluções", () -> abrir(new GestaoDevolucoesView(), "Gestão de Devoluções"));
        adicionar(menu, "Consulta de Pedidos", () -> abrir(new ConsultaHistoricoVendasView(), "Consulta de Pedidos"));
        adicionar(menu, "Tela Balcão / Pagamento", () -> abrir(new TelaBalcaoView(), "Tela de Balcão"));
        add(menu);
    }
    private void adicionar(JPanel menu, String titulo, Runnable acao) { JButton b = new JButton(titulo); b.addActionListener(e -> acao.run()); menu.add(b); }
    private void abrir(JPanel painel, String titulo) { JFrame janela = new JFrame(titulo); janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); janela.add(painel); janela.pack(); janela.setLocationRelativeTo(this); janela.setVisible(true); }
}
