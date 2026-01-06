package service;

import br.univates.alexandria.persistence.DuplicatedKeyException;
import br.univates.alexandria.persistence.IDao;
import br.univates.alexandria.persistence.RecordNotFoundException;
import br.univates.alexandria.persistence.RecordNotReady;
import entities.Order;
import entities.OrderItem;
import entities.Product;
import entities.OrderStatus;
import factory.DAOFactory;

import java.math.BigDecimal;
import java.util.List;

public class OrderService
{

    private final IDao<Order, Integer> orderDAO;
    private final IDao<OrderItem, Integer> orderItemDAO;
    private final IDao<Product, Integer> productDAO;

    public OrderService()
    {
        this.orderDAO = DAOFactory.getOrderDAO();
        this.orderItemDAO = DAOFactory.getOrderItemDAO();
        this.productDAO = DAOFactory.getProductDAO();
    }

    // CRIAR PEDIDO
    public Order createOrder(Order order) throws DuplicatedKeyException, RecordNotReady
    {

        if (order == null)
        {
            throw new IllegalArgumentException("Pedido inválido.");
        }

        order.setStatus(OrderStatus.OPEN);
        orderDAO.create(order);
        return order;
    }

    // ADICIONAR ITEM AO PEDIDO
    public void addItem(Order order, Product product, Integer quantity) throws DuplicatedKeyException, RecordNotReady
    {

        if (order == null || product == null)
        {
            throw new IllegalArgumentException("Pedido ou produto inválido.");
        }

        if (order.isFinished())
        {
            throw new IllegalStateException("Pedido já finalizado.");
        }

        if (quantity == null || quantity <= 0)
        {
            throw new IllegalArgumentException("Quantidade inválida.");
        }

        if (product.getQuantity() < quantity)
        {
            throw new IllegalStateException("Estoque insuficiente.");
        }

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(product.getPrice());

        order.getItems().add(item);
        orderItemDAO.create(item);
    }

    // FINALIZAR PEDIDO (RF5 + RF6)
    public BigDecimal finishOrder(Order order)
            throws RecordNotFoundException
    {

        if (order == null || order.getItems().isEmpty())
        {
            throw new IllegalArgumentException("Pedido inválido ou sem itens.");
        }

        if (order.isFinished()) {
            throw new IllegalStateException("Pedido já está finalizado.");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {

            Product product = item.getProduct();

            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException(
                        "Estoque insuficiente para o produto: "
                                + product.getName());
            }

            product.setQuantity(
                    product.getQuantity() - item.getQuantity());

            productDAO.update(product);

            total = total.add(item.getTotal());
        }

        order.setStatus(OrderStatus.FINISHED);
        orderDAO.update(order);

        return total;
    }


    // LISTAR PEDIDOS
    public List<Order> findAll() {
        return orderDAO.readAll();
    }
}
