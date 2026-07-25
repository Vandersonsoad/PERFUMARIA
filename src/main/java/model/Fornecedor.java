package model;

/**
 * Classe que representa os fornecedores parceiros do sistema StockMaster.
 */
public class Fornecedor {
    
    // Atributos privados para garantir o encapsulamento
    private int id;
    private String razaoSocial;
    private String cnpj;
    private String telefone;
    private String email;

    // Construtor Vazio
    public Fornecedor() {
    }

    // Construtor Completo
    public Fornecedor(int id, String razaoSocial, String cnpj, String telefone, String email) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
    }

    // --- MÉTODOS GETTERS E SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}