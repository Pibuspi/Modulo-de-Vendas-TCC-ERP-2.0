
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Cliente;

public class ClienteDAO {

    private final Connection conexao;

    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente "
                + "(cpf_cnpj, razao_social, nome_fantasia, "
                + "limite_credito, condicao_pagamento, bloqueado, motivo_bloqueio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpfCnpj());
            stmt.setString(2, cliente.getRazaoSocial());
            stmt.setString(3, cliente.getNomeFantasia());
            stmt.setDouble(4, cliente.getLimiteCredito());
            stmt.setString(5, cliente.getCondicaoPagamento());
            stmt.setBoolean(6, cliente.isBloqueado());
            stmt.setString(7, cliente.getMotivoBloqueio());

            return stmt.executeUpdate() > 0;
        }
    }

    public Cliente buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }

        return null;
    }

    public Cliente buscarPorCpfCnpj(String documento) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE cpf_cnpj = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, documento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }

        return null;
    }

    public boolean atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET "
                + "cpf_cnpj = ?, razao_social = ?, nome_fantasia = ?, "
                + "limite_credito = ?, condicao_pagamento = ?, "
                + "bloqueado = ?, motivo_bloqueio = ? "
                + "WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpfCnpj());
            stmt.setString(2, cliente.getRazaoSocial());
            stmt.setString(3, cliente.getNomeFantasia());
            stmt.setDouble(4, cliente.getLimiteCredito());
            stmt.setString(5, cliente.getCondicaoPagamento());
            stmt.setBoolean(6, cliente.isBloqueado());
            stmt.setString(7, cliente.getMotivoBloqueio());
            stmt.setLong(8, cliente.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(long id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setId(rs.getLong("id"));
        cliente.setCpfCnpj(rs.getString("cpf_cnpj"));
        cliente.setRazaoSocial(rs.getString("razao_social"));
        cliente.setNomeFantasia(rs.getString("nome_fantasia"));
        cliente.setLimiteCredito(rs.getDouble("limite_credito"));
        cliente.setCondicaoPagamento(rs.getString("condicao_pagamento"));
        cliente.setBloqueado(rs.getBoolean("bloqueado"));
        cliente.setMotivoBloqueio(rs.getString("motivo_bloqueio"));

        return cliente;
    }
}
