package persistence;

import br.univates.alexandria.persistence.*;
import entities.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAO implements IDao<Product, Integer>
{
    // CREATE
    @Override
    public void create(Product product)
            throws DuplicatedKeyException, RecordNotReady
    {

        String sql = """
            INSERT INTO product (name, price, quantity, category_id)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setInt(4, product.getProductCategory().getId());

            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicatedKeyException();
        } catch (SQLException e) {
            throw new RecordNotReady(e.getMessage());
        }
    }

    // READ (por ID)
    @Override
    public Product read(Integer id) throws RecordNotFoundException
    {

        String sql = "SELECT * FROM product WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new RecordNotFoundException();
            }

            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setQuantity(rs.getInt("quantity"));

            return p;

        }
        catch (SQLException e)
        {
            throw new RecordNotFoundException();
        }
    }

    // READ ALL (sem filtro)
    @Override
    public ArrayList<Product> readAll()
    {

        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setQuantity(rs.getInt("quantity"));
                list.add(p);
            }

        } catch (SQLException ignored) {}

        return list;
    }

    // READ ALL (com filtro)
    @Override
    public ArrayList<Product> readAll(Filter<Product> filter)
    {

        if (filter == null)
        {
            return readAll();
        }

        if (filter instanceof ProductCategoryFilter f) {
            return readByCategory(f.getCategoryId());
        }

        return readAll();
    }

    // MÉTODO AUXILIAR (filtro real)
    private ArrayList<Product> readByCategory(Integer categoryId)
    {

        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE category_id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setQuantity(rs.getInt("quantity"));
                list.add(p);
            }

        }
        catch (SQLException ignored)
        {

        }
        return list;
    }

    // UPDATE
    @Override
    public void update(Product product) throws RecordNotFoundException
    {
        String sql = """
            UPDATE product
            SET name=?, price=?, quantity=?, category_id=?
            WHERE id=?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setInt(4, product.getProductCategory().getId());
            ps.setInt(5, product.getId());

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

    // DELETE
    @Override
    public void delete(Product product) throws RecordNotFoundException
    {

        String sql = "DELETE FROM product WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, product.getId());

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
