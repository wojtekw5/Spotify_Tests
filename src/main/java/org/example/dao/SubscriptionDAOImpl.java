package org.example.dao;

import org.example.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAOImpl implements SubscriptionDAO {
    private final Connection connection;

    public SubscriptionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Subscription subscription) {
        String sql = "INSERT INTO subscriptions (id, type, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subscription.getId());
            statement.setString(2, subscription.getType());
            statement.setDate(3, new java.sql.Date(subscription.getStartDate().getTime()));
            statement.setDate(4, new java.sql.Date(subscription.getEndDate().getTime()));
            statement.setString(5, subscription.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Subscription read(int id) {
        String sql = "SELECT * FROM subscriptions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Subscription(
                            resultSet.getInt("id"),
                            resultSet.getString("type"),
                            resultSet.getDate("start_date"),
                            resultSet.getDate("end_date"),
                            resultSet.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Subscription subscription) {
        String sql = "UPDATE subscriptions SET type = ?, start_date = ?, end_date = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subscription.getType());
            statement.setDate(2, new java.sql.Date(subscription.getStartDate().getTime()));
            statement.setDate(3, new java.sql.Date(subscription.getEndDate().getTime()));
            statement.setString(4, subscription.getStatus());
            statement.setInt(5, subscription.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM subscriptions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Subscription> readAll() {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM subscriptions";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Subscription subscription = new Subscription(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getString("status")
                );
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }
}
