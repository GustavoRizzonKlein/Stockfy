package factory;

import br.univates.alexandria.persistence.IDao;
import entities.*;
import persistence.*;

public class DAOFactory {

    public static IDao<Customer, Integer> getCustomerDAO() {
        return new CustomerDAO();
    }

    public static IDao<Product, Integer> getProductDAO() {
        return new ProductDAO();
    }

    public static IDao<Order, Integer> getOrderDAO() {
        return new OrderDAO();
    }

    public static IDao<OrderItem, Integer> getOrderItemDAO() {
        return new OrderItemDAO();
    }

    public static IDao<ProductCategory, Integer> getProductCategoryDAO() {
        return new ProductCategoryDAO();
    }
}
