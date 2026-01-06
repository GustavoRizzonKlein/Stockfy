package persistence;

import br.univates.alexandria.persistence.*;
import entities.Order;
import entities.OrderStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAO implements IDao<Order, Integer>
{


    @Override
    public void create(Order order) throws RecordNotReady {

        String sql = """
            INSERT INTO client_order (date, status, description, customer_id)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(order.getDate()));
            ps.setString(2, order.getStatus().name());
            ps.setString(3, order.getDescription());
            ps.setInt(4, order.getCustomer().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RecordNotReady(e.getMessage());
        }
    }

    @Override
    public Order read(Integer id) throws RecordNotFoundException {

        String sql = "SELECT * FROM client_order WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new RecordNotFoundException();
            }

            Order order = map(rs);
            return order;

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public ArrayList<Order> readAll() {
        return readByFilter(null);
    }

    @Override
    public ArrayList<Order> readAll(Filter<Order> filter) {

        if (filter instanceof OrderFilter f) {
            return readByFilter(f);
        }

        return readAll();
    }

    private ArrayList<Order> readByFilter(OrderFilter filter) {

        ArrayList<Order> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM client_order WHERE 1=1"
        );

        if (filter != null) {

            if (filter.getStatus() != null) {
                sql.append(" AND status = ?");
            }

            if (filter.getCustomerId() != null) {
                sql.append(" AND customer_id = ?");
            }

            if (filter.getStartDate() != null) {
                sql.append(" AND date >= ?");
            }

            if (filter.getEndDate() != null) {
                sql.append(" AND date <= ?");
            }
        }

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int index = 1;

            if (filter != null) {

                if (filter.getStatus() != null) {
                    ps.setString(index++, filter.getStatus().name());
                }

                if (filter.getCustomerId() != null) {
                    ps.setInt(index++, filter.getCustomerId());
                }

                if (filter.getStartDate() != null) {
                    ps.setDate(index++, Date.valueOf(filter.getStartDate()));
                }

                if (filter.getEndDate() != null) {
                    ps.setDate(index++, Date.valueOf(filter.getEndDate()));
                }
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException ignored) {}

        return list;
    }

    private Order map(ResultSet rs) throws SQLException {

        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setDate(rs.getDate("date").toLocalDate());
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setDescription(rs.getString("description"));

        return order;
    }

    @Override
    public void update(Order order) throws RecordNotFoundException {

        String sql = """
            UPDATE client_order
            SET date=?, status=?, description=?, customer_id=?
            WHERE id=?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(order.getDate()));
            ps.setString(2, order.getStatus().name());
            ps.setString(3, order.getDescription());
            ps.setInt(4, order.getCustomer().getId());
            ps.setInt(5, order.getId());

            if (ps.executeUpdate() == 0) {
                throw new RecordNotFoundException();
            }

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void delete(Order order) throws RecordNotFoundException {

        if (order.isFinished()) {
            throw new RecordNotFoundException();
        }

        String sql = "DELETE FROM client_order WHERE id=? AND status='OPEN'";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, order.getId());

            if (ps.executeUpdate() == 0) {
                throw new RecordNotFoundException();
            }

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }
}
