package persistence;

import br.univates.alexandria.persistence.Filter;
import entities.OrderStatus;

import java.time.LocalDate;

public class OrderFilter implements Filter {

    private OrderStatus status;
    private Integer customerId;
    private LocalDate startDate;
    private LocalDate endDate;

    public OrderFilter() {
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    @Override
    public boolean isAccept(Object o) {
        return true;
    }
}
