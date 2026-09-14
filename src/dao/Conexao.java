package dao;
import java.sql.*;
/** Rafael: fábrica de conexões; credenciais vêm do ambiente, nunca do código versionado. */
public final class Conexao { private Conexao(){} public static Connection abrir() throws SQLException { return DriverManager.getConnection(System.getenv().getOrDefault("DB_URL","jdbc:postgresql://localhost:5432/vendas_tcc"), System.getenv().getOrDefault("DB_USER","postgres"), System.getenv().getOrDefault("DB_PASSWORD","postgres")); } }
