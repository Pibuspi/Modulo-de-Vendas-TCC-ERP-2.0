package dao;
import java.sql.*;
/** Rafael: utilitários para fechar recursos JDBC e executar transações. */
public final class BancoUtil { private BancoUtil(){} public static void fechar(AutoCloseable r){try{if(r!=null)r.close();}catch(Exception ignored){}} public static void rollback(Connection c){try{if(c!=null)c.rollback();}catch(SQLException ignored){}} }
