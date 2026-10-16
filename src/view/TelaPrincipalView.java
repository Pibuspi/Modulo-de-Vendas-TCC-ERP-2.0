package view;

import controller.MainController;
import javax.swing.*;
import java.awt.*;

/** Dashboard executivo do módulo de vendas. */
public class TelaPrincipalView extends JFrame {
    private final MainController controller=new MainController(); private final JLabel clientes=new JLabel("0"), pendentes=new JLabel("0"), faturamento=new JLabel("R$ 0,00");
    public TelaPrincipalView(){ Tema.aplicar(); setTitle("TCC ERP 2.0 - Dashboard de Vendas"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(1000,650); setLocationRelativeTo(null); setLayout(new BorderLayout(12,12)); add(cabecalho(),BorderLayout.NORTH); add(atalhos(),BorderLayout.CENTER); atualizarKpis(); }
    private JPanel cabecalho(){JPanel p=new JPanel(new BorderLayout());JLabel t=new JLabel("Dashboard Executivo de Vendas");t.setFont(t.getFont().deriveFont(Font.BOLD,24f));p.add(t,BorderLayout.WEST);JButton atualizar=new JButton("Atualizar KPIs");atualizar.addActionListener(e->atualizarKpis());p.add(atualizar,BorderLayout.EAST);return p;}
    private JPanel atalhos(){JPanel p=new JPanel(new BorderLayout(10,10));JPanel k=new JPanel(new GridLayout(1,3,10,10));k.add(card("Clientes Ativos",clientes));k.add(card("Pedidos Pendentes",pendentes));k.add(card("Faturamento",faturamento));JPanel b=new JPanel(new GridLayout(0,2,10,10));addButton(b,"Clientes",()->abrir(new CadastroClienteView(),"Clientes"));addButton(b,"Consultar Clientes / Histórico",()->abrir(new ConsultaClienteView(),"Consulta de Clientes"));addButton(b,"Digitação de Pedidos",()->abrir(new DigitacaoPedidoView(),"Pedidos"));addButton(b,"Pricing",()->abrir(new PricingEngineView(),"Pricing"));addButton(b,"Liberação Comercial",()->abrir(new LiberacaoComercialView(),"Liberação"));addButton(b,"Balcão / Pagamento",()->abrir(new TelaBalcaoView(),"Balcão"));addButton(b,"Devoluções",()->abrir(new GestaoDevolucoesView(),"Devoluções"));p.add(k,BorderLayout.NORTH);p.add(b,BorderLayout.CENTER);return p;}
    private JPanel card(String titulo,JLabel valor){JPanel p=new JPanel(new BorderLayout());p.setBorder(BorderFactory.createTitledBorder(titulo));valor.setHorizontalAlignment(SwingConstants.CENTER);valor.setFont(valor.getFont().deriveFont(Font.BOLD,22f));p.add(valor,BorderLayout.CENTER);return p;}
    private void addButton(JPanel p,String text,Runnable action){JButton b=new JButton(text);b.addActionListener(e->action.run());p.add(b);}
    private void abrir(JPanel p,String title){JFrame f=new JFrame(title);f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);f.add(p);f.pack();f.setLocationRelativeTo(this);f.setVisible(true);}
    private void atualizarKpis(){MainController.Kpis k=controller.carregarKpis();clientes.setText(String.valueOf(k.clientesAtivos));pendentes.setText(String.valueOf(k.pedidosPendentes));faturamento.setText(Formato.moeda(k.faturamento));if(k.erro!=null)setTitle("TCC ERP 2.0 - Dashboard de Vendas (modo offline)");}
}
