package persistence;

import br.univates.alexandria.persistence.*;
import entities.OrderItem;
import entities.Product;

import java.sql.*;
import java.util.ArrayList;

public class OrderItemDAO implements IDao<OrderItem, Integer>
{

    @Override
    public void create(OrderItem item)
            throws DuplicatedKeyException, RecordNotReady
    {

        String sql = """
            INSERT INTO order_item (order_id, product_id, quantity, unit_price)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS))
        {

            ps.setInt(1, item.getOrder().getId());
            ps.setInt(2, item.getProduct().getId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getUnitPrice());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                item.setId(rs.getInt(1));
            }

        }
        catch (SQLException e)
        {
            throw new RecordNotReady(e.getMessage());
        }
    }

    @Override
    public OrderItem read(Integer id)
            throws RecordNotFoundException
    {

        String sql = "SELECT * FROM order_item WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next())
            {
                throw new RecordNotFoundException();
            }

            OrderItem item = new OrderItem();
            item.setId(rs.getInt("id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setUnitPrice(rs.getBigDecimal("unit_price"));

            Product product = new Product();
            product.setId(rs.getInt("product_id"));
            item.setProduct(product);

            return item;

        }
        catch (SQLException e)
        {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public ArrayList<OrderItem> readAll() {

        ArrayList<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_item";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getBigDecimal("unit_price"));

                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                item.setProduct(product);

                list.add(item);
            }

        } catch (SQLException ignored) {}

        return list;
    }

    @Override
    public ArrayList<OrderItem> readAll(Filter<OrderItem> filter) {
        return readAll();
    }

    @Override
    public void update(OrderItem item)
            throws RecordNotFoundException {

        String sql = """
            UPDATE order_item
            SET quantity=?, unit_price=?
            WHERE id=?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getQuantity());
            ps.setBigDecimal(2, item.getUnitPrice());
            ps.setInt(3, item.getId());

            if (ps.executeUpdate() == 0) {
                throw new RecordNotFoundException();
            }

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void delete(OrderItem item)
            throws RecordNotFoundException {

        String sql = "DELETE FROM order_item WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getId());

            if (ps.executeUpdate() == 0) {
                throw new RecordNotFoundException();
            }

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }
}
