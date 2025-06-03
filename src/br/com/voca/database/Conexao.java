package br.com.voca.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/voca";
    private static final String USUARIO = "root";
    private static final String SENHA = "Marquinhos20@5";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public static Connection getConexao() {
        return null;
    }
}