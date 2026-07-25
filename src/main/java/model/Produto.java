package model;

/**
 * Classe que representa os produtos no sistema StockMaster.
 * Possui associação com a classe Fornecedor.
 */
public class Produto {
    
    // Atributos privados'
    private int id;
    private String nome;
    private double precoCusto;
    private double precoVenda;
    private int quantidadeEstoque;
    private Fornecedor fornecedor; // Associação direta com a classe Fornecedor

    // Construtor Vazio
    public Produto() {
    }

    // Construtor Completo
    public Produto(int id, String nome, double precoCusto, double precoVenda, int quantidadeEstoque, Fornecedor fornecedor) {
        this.id = id;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.quantidadeEstoque = quantidadeEstoque;
        this.fornecedor = fornecedor;
    }

    // --- MÉTODOS GETTERS E SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}