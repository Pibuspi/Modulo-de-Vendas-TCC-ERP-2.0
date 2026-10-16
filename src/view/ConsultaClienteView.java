package view;

import controller.ConsultaHistoricoController;
import model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/** Consulta de clientes por ID/CPF/nome e histórico de pedidos. */
public class ConsultaClienteView extends JPanel {
    private final ConsultaHistoricoController controller = new ConsultaHistoricoController();
    private final JTextField busca = new JTextField(24);
    private final DefaultTableModel clientesModel = new DefaultTableModel(new Object[]{"ID","CPF/CNPJ","Cliente","Limite","Status"},0) { public boolean isCellEditable(int r,int c){return false;} };
    private final DefaultTableModel pedidosModel = new DefaultTableModel(new Object[]{"Pedido","Status","Total","Criado em"},0) { public boolean isCellEditable(int r,int c){return false;} };
    private final JTable clientes = new JTable(clientesModel), pedidos = new JTable(pedidosModel);
    private final Consumer<Cliente> aoSelecionar;
    private List<Cliente> resultados = List.of();

    public ConsultaClienteView() { this(null); }
    public ConsultaClienteView(Consumer<Cliente> aoSelecionar) {
        this.aoSelecionar = aoSelecionar; Tema.aplicar(); setLayout(new BorderLayout(8,8)); setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JPanel topo=new JPanel(new FlowLayout(FlowLayout.LEFT)); topo.setBorder(BorderFactory.createTitledBorder("Pesquisar cliente por ID, CPF/CNPJ ou nome")); topo.add(busca); JButton pesquisar=new JButton("Pesquisar"); pesquisar.addActionListener(e->pesquisar()); topo.add(pesquisar); add(topo,BorderLayout.NORTH);
        clientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); clientes.getSelectionModel().addListSelectionListener(e->{if(!e.getValueIsAdjusting()) carregarHistorico();});
        pedidos.setFillsViewportHeight(true); JSplitPane split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,new JScrollPane(clientes),new JScrollPane(pedidos)); split.setResizeWeight(.55); add(split,BorderLayout.CENTER);
        JPanel acoes=new JPanel(new FlowLayout(FlowLayout.RIGHT)); JButton selecionar=new JButton("Selecionar cliente"); selecionar.addActionListener(e->selecionar()); acoes.add(selecionar); add(acoes,BorderLayout.SOUTH);
    }
    private void pesquisar(){ try { resultados=controller.pesquisarClientes(busca.getText()); clientesModel.setRowCount(0); for(Cliente c:resultados) clientesModel.addRow(new Object[]{c.getId(),c.getCpfCnpj(),c.getRazaoSocial(),Formato.moeda(c.getLimiteCredito()),c.isBloqueado()?"BLOQUEADO":"ATIVO"}); } catch(Exception e){ erro(e); } }
    private void carregarHistorico(){ int row=clientes.getSelectedRow(); if(row<0)return; try { pedidosModel.setRowCount(0); for(var p:controller.historico(resultados.get(clientes.convertRowIndexToModel(row)))) pedidosModel.addRow(new Object[]{p.getId(),p.getStatus(),Formato.moeda(p.getTotal()),p.getCriadoEm()}); } catch(Exception e){ erro(e); } }
    private void selecionar(){ int row=clientes.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Selecione um cliente.","Consulta",JOptionPane.WARNING_MESSAGE);return;} if(aoSelecionar!=null) aoSelecionar.accept(resultados.get(clientes.convertRowIndexToModel(row))); Window w=SwingUtilities.getWindowAncestor(this); if(w instanceof JDialog)((JDialog)w).dispose(); }
    private void erro(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Consulta",JOptionPane.WARNING_MESSAGE);}
    public static void abrirDialogo(Component parent, Consumer<Cliente> callback){ JDialog d=new JDialog(SwingUtilities.getWindowAncestor(parent),"Consulta de Clientes",Dialog.ModalityType.APPLICATION_MODAL); d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); d.add(new ConsultaClienteView(callback)); d.setSize(780,500); d.setLocationRelativeTo(parent); d.setVisible(true); }
}
