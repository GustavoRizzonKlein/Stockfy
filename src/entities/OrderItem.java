package entities;

import java.math.BigDecimal;

public class OrderItem {

    private Integer id;
    private Order order;
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderItem()
    {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public  BigDecimal getTotal()
    {
        if( unitPrice == null || quantity == null )
        {
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply( BigDecimal.valueOf( quantity ) );
    }

    @Override
    public String toString() {
        return product.getName() + "x" + quantity;
    }
}
