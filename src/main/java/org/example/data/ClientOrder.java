package org.example.data;

import java.time.LocalDateTime;

public record ClientOrder(LocalDateTime orderDate, String companyName,
                          double ordersWeight) implements Comparable<ClientOrder> {

    @Override
    public int compareTo(ClientOrder o) {
        if (this.orderDate == null && o.orderDate == null) return 0;
        if (this.orderDate == null) return -1;
        if (o.orderDate == null) return 1;

        return orderDate.compareTo(o.orderDate);
    }
}
