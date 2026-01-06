package dao;

import java.sql.Connection;
import persistence.ConnectionFactory;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection c = ConnectionFactory.getConnection()) {
            System.out.println("CONECTOU COM MYSQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
