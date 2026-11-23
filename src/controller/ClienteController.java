
package controller;

import java.sql.SQLException;

import dao.ClienteDAO;
import model.Cliente;

/** Matheus Godoy e Lucas de Lima: validação e cadastro de clientes. */
public class ClienteController {

    public boolean validar(Cliente cliente) {

        if (cliente == null) {
            return false;
        }

        String documento = cliente.getCpfCnpj();

        if (documento == null) {
            return false;
        }

        documento = documento.replaceAll("\\D", "");

        if (documento.length() != 11
                && documento.length() != 14) {
            return false;
        }

        if (cliente.getRazaoSocial() == null
                || cliente.getRazaoSocial().isBlank()) {
            return false;
        }

        if (cliente.getCondicaoPagamento() == null
                || cliente.getCondicaoPagamento().isBlank()) {
            return false;
        }

        if (cliente.getLimiteCredito() < 0) {
            return false;
        }

        return true;
    }

    public boolean cadastrar(Cliente cliente, ClienteDAO dao)
            throws SQLException {

        if (!validar(cliente) || dao == null) {
            return false;
        }

        dao.inserir(cliente);
        return true;
    }
}
