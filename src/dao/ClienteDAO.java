
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;

import model.Cliente;

/** Rafael: persistência JDBC de clientes. */
public class ClienteDAO {

    private final Connection conexao;

    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Cliente cliente) throws SQLException {

        String sql = "INSERT INTO cliente "
                + "(cpf_cnpj, razao_social, nome_fantasia, "
                + "limite_credito, condicao_pagamento, "
                + "bloqueado, motivo_bloqueio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt =
                conexao.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCpfCnpj());
            stmt.setString(2, cliente.getRazaoSocial());
            stmt.setString(3, cliente.getNomeFantasia());

            stmt.setBigDecimal(
                    4,
                    BigDecimal.valueOf(
                            cliente.getLimiteCredito()
                    )
            );

            stmt.setString(
                    5,
                    cliente.getCondicaoPagamento()
            );

            stmt.setBoolean(
                    6,
                    cliente.isBloqueado()
            );

            stmt.setString(
                    7,
                    cliente.getMotivoBloqueio()
            );

            stmt.executeUpdate();
        }
    }
}
