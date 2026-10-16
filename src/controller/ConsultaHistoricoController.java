package controller;

import dao.ClienteDAO;
import dao.Conexao;
import model.Cliente;
import model.PedidoVenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Consulta clientes por ID/CPF/nome e histórico de pedidos vinculados. */
public class ConsultaHistoricoController {
    public List<Cliente> pesquisarClientes(String termo) throws SQLException { return new ClienteDAO().buscar(termo); }
    public List<PedidoResumo> historico(Cliente cliente) throws SQLException {
        if (cliente == null || cliente.getId() == null) return new ArrayList<>();
        String sql = "SELECT id, status_comercial, total_liquido, criado_em FROM pedido_venda WHERE cliente_id = ? ORDER BY criado_em DESC";
        List<PedidoResumo> pedidos = new ArrayList<>();
        try (Connection c = Conexao.abrir(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, cliente.getId()); try (ResultSet rs = ps.executeQuery()) { while (rs.next()) pedidos.add(new PedidoResumo(rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getTimestamp(4))); }
        }
        return pedidos;
    }
    public static final class PedidoResumo {
        private final long id; private final String status; private final double total; private final Timestamp criadoEm;
        public PedidoResumo(long id, String status, double total, Timestamp criadoEm) { this.id=id; this.status=status; this.total=total; this.criadoEm=criadoEm; }
        public long getId(){return id;} public String getStatus(){return status;} public double getTotal(){return total;} public Timestamp getCriadoEm(){return criadoEm;}
    }
}
