
package model;

/** Item do pedido; subtotal = quantidade * preço - desconto. */
public class ItemPedido {

    private Produto produto;
    private double quantidade;
    private double precoUnitario;
    private double desconto;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        validarNumero(quantidade, "Quantidade");
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        validarNumero(precoUnitario, "Preço");
        if (precoUnitario < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        this.precoUnitario = precoUnitario;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        validarNumero(desconto, "Desconto");
        if (desconto < 0) {
            throw new IllegalArgumentException("Desconto não pode ser negativo.");
        }
        this.desconto = desconto;
    }

    public double getSubtotal() {
        return quantidade * precoUnitario - desconto;
    }

    private void validarNumero(double valor, String campo) {
        if (!Double.isFinite(valor)) {
            throw new IllegalArgumentException(campo + " inválido.");
        }
    }
}
