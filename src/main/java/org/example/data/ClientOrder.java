package org.example.data;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class ClientOrder implements Comparable<ClientOrder> {
    private final LocalDateTime orderDate;
    private final String companyName;
    private final double ordersWeight;
    private double ordersPrice;

    public ClientOrder(LocalDateTime orderDate, String companyName, double ordersWeight) {
        this.orderDate = orderDate;
        this.companyName = companyName;
        this.ordersWeight = ordersWeight;
    }

    public String getCompanyName() {
        return companyName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrdersPrice(double ordersPrice) {
        this.ordersPrice = ordersPrice;
    }

    public String getOrdersPrice() {
        DecimalFormat df = new DecimalFormat("0.00");
        return  df.format(ordersPrice);
    }

    public double getOrdersWeight() {
        return ordersWeight;
    }

    @Override
    public int compareTo(ClientOrder o) {
        if (this.orderDate == null && o.orderDate == null) return 0;
        if (this.orderDate == null) return -1;
        if (o.orderDate == null) return 1;

        return orderDate.compareTo(o.orderDate);
    }
}
