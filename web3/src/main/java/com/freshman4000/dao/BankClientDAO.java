package com.freshman4000.dao;

import com.freshman4000.models.BankClient;

import java.sql.*;
import java.util.List;

public class BankClientDAO {

    private Connection connection;

    public BankClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<BankClient> getAllBankClient() {
        return null;
    }

    public boolean validateClient(String name, String password) {
        return false;
    }

    public void updateClientsMoney(String name, String password, Long transactValue) {

    }

    public BankClient getClientById(long id) throws SQLException {
        return null;
    }

    public boolean isClientHasSum(String name, Long expectedSum) {
        return false;
    }

    public long getClientIdByName(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        Long id = result.getLong(1);
        result.close();
        stmt.close();
        return id;
    }

    public BankClient getClientByName(String name) {
        BankClient result = new BankClient();
        try(PreparedStatement pst = connection.prepareStatement("SELECT * FROM bank_client WHERE name = ?")) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            while(resultSet.next()) {
                result = new BankClient(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4));
            }
        } catch (SQLException e) {
            result = null;
        }
        return result;
    }

    public void addClient(BankClient client) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bank_client (name, password, money) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPassword());
            preparedStatement.setLong(3, client.getMoney());
            preparedStatement.executeUpdate();
        }
    }

    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists bank_client (id bigint auto_increment, name varchar(256), password varchar(256), money bigint, primary key (id))");
        stmt.close();
    }

    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS bank_client");
        stmt.close();
    }
}
