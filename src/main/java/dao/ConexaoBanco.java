package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados.
 */
public class ConexaoBanco {
    
    // Configurações do Banco de Dados (Ajuste o nome do banco após o localhost:3306/)
    private static final String URL = "jdbc:mysql://localhost:3306/stockmaster_db?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root"; // Usuário padrão do MySQL
    private static final String SENHA = "1234";   // Senha padrão configurada
    
    private static final Logger logger = Logger.getLogger(ConexaoBanco.class.getName());

    /**
     * Abre uma nova conexão com o banco de dados.
     * @return Connection objeto de conexão ativo
     */
    public static Connection getConexao() {
        try {
            // Carrega o Driver do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException | SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao conectar ao banco de dados: ", ex);
            return null;
        }
    }

    /**
     * Fecha a conexão com o banco de dados 
     * @param conn Conexão a ser fechada
     */
    public static void fecharConexao(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Erro ao fechar a conexão: ", ex);
            }
        }
    }

    /**
     * Método de teste para executar a conexão diretamente pelo NetBeans.
     */
    public static void main(String[] args) {
        System.out.println("Tentando conectar ao banco de dados...");
        Connection conn = ConexaoBanco.getConexao();
        
        if (conn != null) {
            System.out.println("🎉 SUCESSO! O Java conectou ao MySQL perfeitamente!");
            ConexaoBanco.fecharConexao(conn);
        } else {
            System.out.println("❌ FALHA! A conexão falhou. Verifique se o MySQL está ativo no Workbench.");
        }
    }
}