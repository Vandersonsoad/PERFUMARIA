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
    
    // Configurações do Banco de Dados
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
        System.out.println("=== INICIANDO TESTE DE CONEXÃO ===");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("1. Driver MySQL carregado com sucesso!");
            
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            
            if (conn != null) {
                System.out.println("🎉 SUCESSO! Conexão realizada com o banco stockmaster_db!");
                System.out.println("Banco conectado: " + conn.getCatalog());
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERRO: O Driver não foi encontrado no projeto.");
        } catch (SQLException e) {
            System.err.println("❌ FALHA NA CONEXÃO COM O BANCO:");
            System.err.println("   Mensagem do MySQL: " + e.getMessage());
            System.err.println("   Código do erro: " + e.getErrorCode());
            
            if (e.getErrorCode() == 1045) {
                System.err.println("   👉 Causa: Usuário 'root' ou senha '1234' incorretos.");
            } else if (e.getErrorCode() == 1049) {
                System.err.println("   👉 Causa: O banco 'stockmaster_db' não existe no MySQL. Crie-o executando: CREATE DATABASE stockmaster_db;");
            } else if (e.getErrorCode() == 0 || e.getErrorCode() == 1042) {
                System.err.println("   👉 Causa: O serviço do MySQL está DESLIGADO no Windows/XAMPP.");
            }
        }
    }
}