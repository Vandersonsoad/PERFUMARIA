package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Venda;

/**
 * Classe responsável pelas operações de banco de dados para a entidade Venda.
 */
public class VendaDAO {
    
    private static final Logger logger = Logger.getLogger(VendaDAO.class.getName());

    /**
     * Registra uma nova venda no banco de dados.
     * @param venda Objeto contendo os dados da venda a ser realizada
     * @return true se salva com sucesso, false caso contrário
     */
    public boolean salvar(Venda venda) {
        Connection conn = ConexaoBanco.getConexao();
        PreparedStatement stmt = null;
        
        // A coluna data_venda será preenchida automaticamente pelo banco com o CURRENT_TIMESTAMP
        String sql = "INSERT INTO venda (valor_total, usuario_id) VALUES (?, ?)";
        
        try {
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, venda.getValorTotal());
                
                // Associa o ID do usuário/vendedor logado se ele existir
                if (venda.getUsuario() != null) {
                    stmt.setInt(2, venda.getUsuario().getId());
                } else {
                    stmt.setNull(2, java.sql.Types.INTEGER);
                }
                
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao registrar venda: ", ex);
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