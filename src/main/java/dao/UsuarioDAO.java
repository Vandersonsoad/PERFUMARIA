package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

/**
 * Classe responsável pelas operações de banco de dados para a entidade Usuario.
 */
public class UsuarioDAO {
    
    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    /**
     * Valida o login e a senha do usuário para permitir o acesso ao sistema.
     * @param login O login digitado
     * @param senha A senha digitada
     * @return Usuario preenchido se encontrado, ou null se as credenciais forem inválidas
     */
    public Usuario autenticarUsuario(String login, String senha) {
        Connection conn = ConexaoBanco.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
        
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, login);
                stmt.setString(2, senha);
                
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    return usuario;
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao autenticar usuário: ", ex);
        } finally {
            // Garante o fechamento dos recursos de forma segura
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexaoBanco.fecharConexao(conn);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Erro ao fechar recursos: ", ex);
            }
        }
        
        return null; // Retorna null se não encontrar o usuário
    }
}