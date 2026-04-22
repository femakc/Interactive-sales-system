package org.example.services;

import org.example.AppConfig;
import org.example.data.ClientOrder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class DiscountCounter {

    private final double cost = AppConfig.getDouble("base.cost");
    private final int discount = AppConfig.getInteger("start.discount");
    private final int discountStep = AppConfig.getInteger("discount.step");

    public void calculation(List<ClientOrder> clients) {

        if (clients == null || clients.isEmpty()) return;

        List<ClientOrder> sorted = clients.stream()
                .sorted(Comparator.comparing(ClientOrder::getOrderDate))
                .toList();

        //можно через for(читабельнее) но задача стоит использовать Strea API
        IntStream.range(0, sorted.size())
                .forEach(i -> {
                    ClientOrder client = sorted.get(i);

                    int currentDiscount = Math.max(discount - i * discountStep, 0);

                    double pricePerUnit = cost * (1 - currentDiscount / 100.0);
                    double totalPrice = client.getOrdersWeight() * pricePerUnit;

                    client.setOrdersPrice(totalPrice);
                });
    }
}
