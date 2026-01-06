package persistence;

import br.univates.alexandria.persistence.*;
import entities.ProductCategory;

import java.sql.*;
import java.util.ArrayList;

public class ProductCategoryDAO implements IDao<ProductCategory, Integer> {


    @Override
    public void create(ProductCategory category)
            throws DuplicatedKeyException, RecordNotReady {

        String sql = "INSERT INTO product_category (name) VALUES (?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, category.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                category.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RecordNotReady(e.getMessage());
        }
    }


    @Override
    public ProductCategory read(Integer id)
            throws RecordNotFoundException {

        String sql = "SELECT * FROM product_category WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new RecordNotFoundException();
            }

            ProductCategory c = new ProductCategory();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            return c;

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }


    @Override
    public ArrayList<ProductCategory> readAll() {

        ArrayList<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM product_category";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductCategory c = new ProductCategory();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                list.add(c);
            }

        } catch (SQLException ignored) {}

        return list;
    }

    @Override
    public ArrayList<ProductCategory> readAll(Filter<ProductCategory> filter) {
        return readAll();
    }

    @Override
    public void update(ProductCategory category)
            throws RecordNotFoundException {

        String sql = "UPDATE product_category SET name=? WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());

            if (ps.executeUpdate() == 0) {
                throw new RecordNotFoundException();
            }

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void delete(ProductCategory category)
            throws RecordNotFoundException {

        String sql = "DELETE FROM product_category WHERE id=?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, category.getId());

            if (ps.executeUpdate() == 0) {
                throw new RecordNotFoundException();
            }

        } catch (SQLException e) {
            throw new RecordNotFoundException();
        }
    }
}
