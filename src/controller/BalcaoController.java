package controller;

import dao.ProdutoDAO;
import model.ItemVenda;
import model.Produto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Yuri: catálogo JDBC, validação de estoque e carrinho de compras em memória. */
public class BalcaoController {
    private final ProdutoDAO produtoDAO;
    private final List<ItemVenda> carrinho = new ArrayList<>();
    private String ultimoErro;

    public BalcaoController() { this(new ProdutoDAO()); }
    public BalcaoController(ProdutoDAO produtoDAO) { this.produtoDAO = produtoDAO; }

    public List<Produto> buscar(String termo) {
        try {
            ultimoErro = null;
            return produtoDAO.buscar(termo);
        } catch (SQLException erro) {
            ultimoErro = "Não foi possível consultar o catálogo: " + erro.getMessage();
            return Collections.emptyList();
        }
    }

    public void adicionar(String codigo, double quantidade) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("Selecione um produto.");
        if (!Double.isFinite(quantidade) || quantidade <= 0) throw new IllegalArgumentException("Informe uma quantidade válida.");
        try {
            Produto produto = produtoDAO.buscarPorCodigo(codigo);
            if (produto == null) throw new IllegalArgumentException("Produto não encontrado.");
            double quantidadeNoCarrinho = carrinho.stream()
                .filter(item -> item.getProduto().getCodigo().equalsIgnoreCase(codigo))
                .mapToDouble(ItemVenda::getQuantidade).sum();
            if (quantidadeNoCarrinho + quantidade > produto.getQuantidade()) {
                throw new IllegalArgumentException("Quantidade superior ao estoque disponível.");
            }
            ItemVenda existente = carrinho.stream()
                .filter(item -> item.getProduto().getCodigo().equalsIgnoreCase(codigo)).findFirst().orElse(null);
            if (existente == null) carrinho.add(new ItemVenda(produto, quantidade));
            else existente.setQuantidade(existente.getQuantidade() + quantidade);
        } catch (SQLException erro) {
            throw new IllegalStateException("Não foi possível validar o estoque: " + erro.getMessage(), erro);
        }
    }

    public void remover(int indice) {
        if (indice < 0 || indice >= carrinho.size()) throw new IllegalArgumentException("Selecione um item do carrinho.");
        carrinho.remove(indice);
    }

    public void remover(String codigo) {
        carrinho.removeIf(item -> item.getProduto().getCodigo().equalsIgnoreCase(codigo));
    }

    public void alterarQuantidade(String codigo, double quantidade) {
        if (!Double.isFinite(quantidade) || quantidade <= 0) throw new IllegalArgumentException("Informe uma quantidade válida.");
        ItemVenda item = carrinho.stream().filter(i -> i.getProduto().getCodigo().equalsIgnoreCase(codigo)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Produto não está no carrinho."));
        if (quantidade > item.getProduto().getQuantidade()) throw new IllegalArgumentException("Quantidade superior ao estoque disponível.");
        item.setQuantidade(quantidade);
    }

    public void limparCarrinho() { carrinho.clear(); }

    public List<ItemVenda> getCarrinho() { return Collections.unmodifiableList(carrinho); }
    public double calcularTotal() { return carrinho.stream().mapToDouble(ItemVenda::getSubtotal).sum(); }
    public String getUltimoErro() { return ultimoErro; }
}
