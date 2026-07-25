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

/**
 * Classe responsável pelas operações de CRUD no banco de dados para a entidade Fornecedor.
 */
public class FornecedorDAO {
    
    private static final Logger logger = Logger.getLogger(FornecedorDAO.class.getName());

    /**
     * Insere um novo fornecedor no banco de dados.
     * @param fornecedor Objeto contendo os dados a serem salvos
     * @return true se salvo com sucesso, false caso contrário
     */
    public boolean salvar(Fornecedor fornecedor) {
        Connection conn = ConexaoBanco.getConexao();
        PreparedStatement stmt = null;
        
        String sql = "INSERT INTO fornecedor (razao_social, cnpj, telefone, email) VALUES (?, ?, ?, ?)";
        
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, fornecedor.getRazaoSocial());
                stmt.setString(2, fornecedor.getCnpj());
                stmt.setString(3, fornecedor.getTelefone());
                stmt.setString(4, fornecedor.getEmail());
                
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao salvar fornecedor: ", ex);
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

    /**
     * Retorna uma lista com todos os fornecedores cadastrados no banco de dados.
     * @return List de Fornecedor
     */
    public List<Fornecedor> listarTodos() {
        Connection conn = ConexaoBanco.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor ORDER BY razao_social";
        
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                
                while (rs.next()) {
                    Fornecedor f = new Fornecedor();
                    f.setId(rs.getInt("id"));
                    f.setRazaoSocial(rs.getString("razao_social"));
                    f.setCnpj(rs.getString("cnpj"));
                    f.setTelefone(rs.getString("telefone"));
                    f.setEmail(rs.getString("email"));
                    
                    lista.add(f);
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao listar fornecedores: ", ex);
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
    
    public boolean excluir(int id) {
        Connection conn = ConexaoBanco.getConexao();
        PreparedStatement stmt = null;
        
        String sql = "DELETE FROM fornecedor WHERE id = ?";
        
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao excluir fornecedor: ", ex);
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
    
    public Fornecedor buscarPorRazao(String razao) {
        Connection conn = ConexaoBanco.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Fornecedor f = null;
        
        String sql = "SELECT * FROM fornecedor WHERE razao_social = ?";
        
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, razao);
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    f = new Fornecedor();
                    f.setId(rs.getInt("id"));
                    f.setRazaoSocial(rs.getString("razao_social"));
                    f.setCnpj(rs.getString("cnpj"));
                    f.setTelefone(rs.getString("telefone"));
                    f.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao buscar fornecedor por razão social: ", ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexaoBanco.fecharConexao(conn);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Erro ao fechar recursos: ", ex);
            }
        }
        return f;}
}