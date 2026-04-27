package org.example.services.orders;

import org.example.data.ClientOrder;
import org.example.data.OrderReport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderDiscountService {

    public List<OrderReport> calculate(List<ClientOrder> clients, double cost, int discount, int discountStep) {

        if (clients == null || clients.isEmpty()) {
            return List.of();
        }

        List<ClientOrder> sorted = clients.stream()
                .sorted(Comparator.comparing(ClientOrder::orderDate))
                .toList();

        List<OrderReport> result = new ArrayList<>();

        for (ClientOrder client : sorted) {
            int currentDiscount = discount - discountStep;
            double pricePerUnit = cost * (1 - currentDiscount / 100.0);
            double totalPrice = client.ordersWeight() * pricePerUnit;

            result.add(new OrderReport(
                    client.companyName(),
                    totalPrice
            ));
        }
        return result;

    }
}
