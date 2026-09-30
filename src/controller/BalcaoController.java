package controller;

import model.ItemVenda;
import model.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Eduardo Yuri: busca de produtos, composição do carrinho e total da compra. */
public class BalcaoController {
    private final List<Produto> produtos = new ArrayList<>();
    private final List<ItemVenda> carrinho = new ArrayList<>();
    public BalcaoController() {
        produtos.add(new Produto("001", "Produto de demonstração", 100, 0, 10.00));
        produtos.add(new Produto("002", "Item de balcão", 50, 0, 25.50));
    }
    public List<Produto> buscar(String termo) {
        if (termo == null || termo.isBlank()) return new ArrayList<>(produtos);
        String filtro = termo.toLowerCase();
        return produtos.stream().filter(p -> p.getCodigo().equalsIgnoreCase(termo)
            || p.getProduto().toLowerCase().contains(filtro)
            || String.valueOf(p.getPreco()).contains(filtro)).collect(Collectors.toList());
    }
    public void adicionar(String codigo, double quantidade) {
        Produto produto = produtos.stream().filter(p -> p.getCodigo().equalsIgnoreCase(codigo)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        if (quantidade <= 0 || quantidade > produto.getQuantidade()) throw new IllegalArgumentException("Quantidade indisponível.");
        carrinho.add(new ItemVenda(produto, quantidade));
    }
    public void remover(String codigo) { carrinho.removeIf(i -> i.getProduto().getCodigo().equalsIgnoreCase(codigo)); }
    public List<ItemVenda> getCarrinho() { return carrinho; }
    public double calcularTotal() { return carrinho.stream().mapToDouble(ItemVenda::getSubtotal).sum(); }
}
