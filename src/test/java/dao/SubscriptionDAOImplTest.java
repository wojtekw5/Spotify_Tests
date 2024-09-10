package dao;

import org.example.Subscription;
import org.example.dao.SubscriptionDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriptionDAOImplTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    private SubscriptionDAOImpl subscriptionDAO;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        statement = mock(Statement.class);
        subscriptionDAO = new SubscriptionDAOImpl(connection);
    }

    @Test
    void testCreate() throws SQLException {
        String sql = "INSERT INTO subscriptions (id, type, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Subscription subscription = new Subscription(1, "Premium", new Date(), new Date(), "Active");
        subscriptionDAO.create(subscription);

        verify(preparedStatement).setInt(1, subscription.getId());
        verify(preparedStatement).setString(2, subscription.getType());
        verify(preparedStatement).setDate(3, new java.sql.Date(subscription.getStartDate().getTime()));
        verify(preparedStatement).setDate(4, new java.sql.Date(subscription.getEndDate().getTime()));
        verify(preparedStatement).setString(5, subscription.getStatus());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRead() throws SQLException {
        String sql = "SELECT * FROM subscriptions WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("type")).thenReturn("Premium");
        when(resultSet.getDate("start_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(resultSet.getDate("end_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(resultSet.getString("status")).thenReturn("Active");

        Subscription subscription = subscriptionDAO.read(1);

        assertNotNull(subscription);
        assertEquals(1, subscription.getId());
        assertEquals("Premium", subscription.getType());
        assertEquals("Active", subscription.getStatus());
    }

    @Test
    void testUpdate() throws SQLException {
        String sql = "UPDATE subscriptions SET type = ?, start_date = ?, end_date = ?, status = ? WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Subscription subscription = new Subscription(1, "Premium", new Date(), new Date(), "Active");
        subscriptionDAO.update(subscription);

        verify(preparedStatement).setString(1, subscription.getType());
        verify(preparedStatement).setDate(2, new java.sql.Date(subscription.getStartDate().getTime()));
        verify(preparedStatement).setDate(3, new java.sql.Date(subscription.getEndDate().getTime()));
        verify(preparedStatement).setString(4, subscription.getStatus());
        verify(preparedStatement).setInt(5, subscription.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        String sql = "DELETE FROM subscriptions WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        subscriptionDAO.delete(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testReadAll() throws SQLException {
        String sql = "SELECT * FROM subscriptions";
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(sql)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("type")).thenReturn("Premium", "Basic");
        when(resultSet.getDate("start_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(resultSet.getDate("end_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(resultSet.getString("status")).thenReturn("Active", "Inactive");

        List<Subscription> subscriptions = subscriptionDAO.readAll();

        assertEquals(2, subscriptions.size());

        Subscription subscription1 = subscriptions.get(0);
        assertEquals(1, subscription1.getId());
        assertEquals("Premium", subscription1.getType());
        assertEquals("Active", subscription1.getStatus());

        Subscription subscription2 = subscriptions.get(1);
        assertEquals(2, subscription2.getId());
        assertEquals("Basic", subscription2.getType());
        assertEquals("Inactive", subscription2.getStatus());
    }
}
