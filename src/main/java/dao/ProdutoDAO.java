package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Fornecedor;
import model.Produto;

/**
 * Classe responsável pelas operações de CRUD no banco de dados para a entidade Produto.
 */
public class ProdutoDAO {
    
    private static final Logger logger = Logger.getLogger(ProdutoDAO.class.getName());

    /**
     * Insere um novo produto associado a um fornecedor no banco de dados.
     * @param produto Objeto contendo os dados do produto
     * @return true se salvo com sucesso, false caso contrário
     */
    public boolean salvar(Produto produto) {
        Connection conn = ConexaoBanco.getConexao();
        PreparedStatement stmt = null;
        
        String sql = "INSERT INTO produto (nome, preco_custo, preco_venda, quantidade_estoque, fornecedor_id) VALUES (?, ?, ?, ?, ?)";
        
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, produto.getNome());
                stmt.setDouble(2, produto.getPrecoCusto());
                stmt.setDouble(3, produto.getPrecoVenda());
                stmt.setInt(4, produto.getQuantidadeEstoque());
                
                // Pega o ID do fornecedor que está dentro do objeto produto
                if (produto.getFornecedor() != null) {
                    stmt.setInt(5, produto.getFornecedor().getId());
                } else {
                    stmt.setNull(5, java.sql.Types.INTEGER);
                }
                
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao salvar produto: ", ex);
        } finally {
            try {
                if (stmt != null) stmt.close();
                ConexaoBanco.fecharConexao(conn);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Erro ao fechar recursos: ", ex);
            }
        }
        return false;
    }
    
    public boolean excluir(int id) {
    Connection conn = ConexaoBanco.getConexao();
    PreparedStatement stmt = null;
    
    String sql = "DELETE FROM produto WHERE id = ?";
    
    try {
        if (conn != null) {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            return true;
        }
    } catch (SQLException ex) {
        logger.log(Level.SEVERE, "Erro ao excluir produto: ", ex);
    } finally {
        try {
            if (stmt != null) stmt.close();
            ConexaoBanco.fecharConexao(conn);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao fechar recursos: ", ex);
        }
    }
    return false;}
    
    public Produto buscarPorNome(String nome) {
    Connection conn = ConexaoBanco.getConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Produto p = null;
    
    String sql = "SELECT * FROM produto WHERE nome = ?";
    
    try {
        if (conn != null) {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPrecoCusto(rs.getDouble("preco_custo"));
                p.setPrecoVenda(rs.getDouble("preco_venda"));
                p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
            }
        }
    } catch (SQLException ex) {
        logger.log(Level.SEVERE, "Erro ao buscar produto por nome: ", ex);
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            ConexaoBanco.fecharConexao(conn);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao fechar recursos: ", ex);
        }
    }
    return p;
}

    /**
     * Lista todos os produtos fazendo a junção (JOIN) com a tabela de fornecedores.
     * @return List de Produto com seus respectivos fornecedores populados
     */
    public List<Produto> listarTodos() {
        Connection conn = ConexaoBanco.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Produto> lista = new ArrayList<>();
        
        // SQL com INNER JOIN para buscar os dados das duas tabelas de uma vez só
        String sql = "SELECT p.*, f.razao_social FROM produto p "
                   + "INNER JOIN fornecedor f ON p.fornecedor_id = f.id "
                   + "ORDER BY p.nome";
        
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPrecoCusto(rs.getDouble("preco_custo"));
                    p.setPrecoVenda(rs.getDouble("preco_venda"));
                    p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                    
                    // Cria o objeto fornecedor e define apenas o ID e a Razão Social vinda do JOIN
                    Fornecedor f = new Fornecedor();
                    f.setId(rs.getInt("fornecedor_id"));
                    f.setRazaoSocial(rs.getString("razao_social"));
                    
                    // Associa o fornecedor ao produto
                    p.setFornecedor(f);
                    
                    lista.add(p);
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao listar produtos: ", ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexaoBanco.fecharConexao(conn);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Erro ao fechar recursos: ", ex);
            }
        }
        return lista;
    }
    
    public boolean atualizarEstoque(int id, int novaQuantidade) {
    Connection conn = ConexaoBanco.getConexao();
    PreparedStatement stmt = null;
    
    String sql = "UPDATE produto SET quantidade_estoque = ? WHERE id = ?";
    
    try {
        if (conn != null) {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            return true;
        }
    } catch (SQLException ex) {
        logger.log(Level.SEVERE, "Erro ao atualizar estoque do produto: ", ex);
    } finally {
        try {
            if (stmt != null) stmt.close();
            ConexaoBanco.fecharConexao(conn);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao fechar recursos: ", ex);
        }
    }
    return false;
}
}