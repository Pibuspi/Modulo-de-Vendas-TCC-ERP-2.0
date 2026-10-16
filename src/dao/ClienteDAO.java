package dao;

import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Rafael: persistência e consultas parametrizadas de clientes. */
public class ClienteDAO {
    public List<Cliente> buscar(String termo) throws SQLException {
        String filtro = termo == null ? "" : termo.trim();
        String like = "%" + filtro + "%";
        String sql = "SELECT id, cpf_cnpj, razao_social, nome_fantasia, limite_credito, condicao_pagamento, bloqueado, motivo_bloqueio FROM cliente WHERE (? = '' OR cpf_cnpj ILIKE ? OR razao_social ILIKE ? OR COALESCE(nome_fantasia,'') ILIKE ?) ORDER BY razao_social";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection c = Conexao.abrir(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, filtro); ps.setString(2, like); ps.setString(3, like); ps.setString(4, like);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) clientes.add(mapear(rs)); }
        }
        return clientes;
    }
    public Cliente buscarPorIdOuDocumento(String termo) throws SQLException {
        List<Cliente> encontrados = buscar(termo);
        return encontrados.isEmpty() ? null : encontrados.get(0);
    }
    public boolean existeClienteAtivo(Connection c, Long id) throws SQLException {
        if (id == null) return false;
        try (PreparedStatement ps = c.prepareStatement("SELECT 1 FROM cliente WHERE id = ? AND bloqueado = FALSE")) {
            ps.setLong(1, id); try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }
    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id")); cliente.setCpfCnpj(rs.getString("cpf_cnpj"));
        cliente.setRazaoSocial(rs.getString("razao_social")); cliente.setNomeFantasia(rs.getString("nome_fantasia"));
        cliente.setLimiteCredito(rs.getDouble("limite_credito")); cliente.setCondicaoPagamento(rs.getString("condicao_pagamento"));
        cliente.setBloqueado(rs.getBoolean("bloqueado")); cliente.setMotivoBloqueio(rs.getString("motivo_bloqueio"));
        return cliente;
    }
    public void reservarMetodos() { }
}
