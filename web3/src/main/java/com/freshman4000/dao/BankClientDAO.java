package com.freshman4000.dao;

import com.freshman4000.models.BankClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankClientDAO {

    private Connection connection;

    public BankClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<BankClient> getAllBankClient() throws SQLException {
        List<BankClient> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bank_client");
            while (resultSet.next()) {
                result.add(new BankClient(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4)));
            }
            resultSet.close();
        }
        return result;
    }

    public boolean validateClient(String name, String password) throws SQLException {
        boolean result;
        try (PreparedStatement pst = connection.prepareStatement("SELECT password FROM bank_client WHERE name= ?")) {
            pst.setString(1, name);
            ResultSet rst = pst.executeQuery();
            result = rst.next() && password.equals(rst.getString(1));
            rst.close();
        }
        return result;
    }

    public void updateClientsMoney(String name, String password, Long transactValue) throws SQLException {
        boolean fundsSufficiency = true;
        if (transactValue < 0) {
            fundsSufficiency = isClientHasSum(name, -transactValue);
        }
        //validation of user
        if (validateClient(name, password) && fundsSufficiency) {
            //if transaction is negative - validate sufficiency of funds
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bank_client SET money= ? WHERE name= ?");
                 PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT money FROM bank_client WHERE name = ?")) {
                preparedStatement1.setString(1, name);
                ResultSet rst = preparedStatement1.executeQuery();
                rst.next();
                long newUserBalance = rst.getLong(1) + transactValue;
                preparedStatement.setLong(1, newUserBalance);
                preparedStatement.setString(2, name);
                preparedStatement.executeUpdate();
                rst.close();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public BankClient getClientById(long id) throws SQLException {
        BankClient result;
        try (PreparedStatement pst = connection.prepareStatement("SELECT * FROM bank_client WHERE id = ?")) {
            pst.setLong(1, id);
            ResultSet rst = pst.executeQuery();
            rst.next();
            result = new BankClient(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getLong(4));
            rst.close();
        }
        return result;
    }

    public boolean isClientHasSum(String name, Long expectedSum) throws SQLException {
        boolean result;
        try (PreparedStatement pst = connection.prepareStatement("SELECT money FROM bank_client WHERE name = ?")) {
            pst.setString(1, name);
            ResultSet rst = pst.executeQuery();
            rst.next();
            result = expectedSum <= rst.getLong(1);
            rst.close();
        }
        return result;
    }
    //method that came from origin SQL injection - non safe
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

    public BankClient getClientByName(String name) throws SQLException {
        BankClient result;
        try (PreparedStatement pst = connection.prepareStatement("SELECT * FROM bank_client WHERE name = ?")) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                result = new BankClient(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4));
            } else result = null;
            resultSet.close();
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

    public boolean deleteClient(String name) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("DELETE FROM bank_client WHERE name = ?")) {
            pst.setString(1, name);
            pst.executeUpdate();
            return true;
        }
    }
}