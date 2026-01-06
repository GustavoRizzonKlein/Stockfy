package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private Integer id;
    private OrderStatus status;
    private LocalDate date;
    private String description;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();

    public Order()
    {
        this.status = OrderStatus.OPEN;
        this.date = LocalDate.now();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public  boolean isFinished()
    {
        return status == OrderStatus.FINISHED;
    }

}
