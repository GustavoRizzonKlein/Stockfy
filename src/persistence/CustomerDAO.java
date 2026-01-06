package persistence;

import br.univates.alexandria.persistence.*;
import entities.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO implements IDao<Customer, Integer>
{

    @Override
    public void create(Customer customer) throws RecordNotReady
    {

        String sql = """
            INSERT INTO customer (name, cpf, city, uf)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS))
        {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getCpf());
            ps.setString(3, customer.getCity());
            ps.setString(4, customer.getUf());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                customer.setId(rs.getInt(1));
            }

        }
        catch (SQLException e)
        {
            throw new RecordNotReady(e.getMessage());
        }
    }

    @Override
    public Customer read(Integer id) throws RecordNotFoundException {

        String sql = "SELECT * FROM customer WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new RecordNotFoundException();
            }

            Customer c = new Customer();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setCpf(rs.getString("cpf"));
            c.setCity(rs.getString("city"));
            c.setUf(rs.getString("uf"));

            return c;

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public ArrayList<Customer> readAll()
    {

        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setCpf(rs.getString("cpf"));
                c.setCity(rs.getString("city"));
                c.setUf(rs.getString("uf"));
                list.add(c);
            }

        } catch (SQLException ignored) {}

        return list;
    }

    @Override
    public ArrayList<Customer> readAll(Filter<Customer> filter)
    {
        return readAll();
    }

    @Override
    public void update(Customer customer) throws RecordNotFoundException {

        String sql = """
            UPDATE customer
            SET name=?, cpf=?, city=?, uf=?
            WHERE id=?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getCpf());
            ps.setString(3, customer.getCity());
            ps.setString(4, customer.getUf());
            ps.setInt(5, customer.getId());

            if (ps.executeUpdate() == 0)
            {
                throw new RecordNotFoundException();
            }

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void delete(Customer customer) throws RecordNotFoundException
    {

        String sql = "DELETE FROM customer WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {

            ps.setInt(1, customer.getId());

            if (ps.executeUpdate() == 0)
            {
                throw new RecordNotFoundException();
            }

        }
        catch (SQLException e)
        {
            throw new RecordNotFoundException();
        }
    }
}
