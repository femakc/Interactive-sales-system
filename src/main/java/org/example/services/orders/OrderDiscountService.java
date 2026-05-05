package org.example.services.orders;

import org.example.data.ClientOrder;
import org.example.data.OrderReport;
import org.example.exceptions.DiscountException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderDiscountService {

    public List<OrderReport> calculate(
            List<ClientOrder> clients,
            BigDecimal cost,
            BigDecimal discount,
            BigDecimal discountStep) {

        if (clients == null || clients.isEmpty()) {
            return List.of();
        }

        if (discount == null || discount.compareTo(BigDecimal.ZERO) < 0
                || discount.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new DiscountException(
                    "Discount must be between 0 and 100, got: " + discount);
        }

        List<ClientOrder> sorted = clients.stream()
                .sorted(Comparator.comparing(ClientOrder::orderDate))
                .toList();

        List<OrderReport> result = new ArrayList<>();

        for (ClientOrder client : sorted) {

            BigDecimal pricePerUnit = cost.multiply(
                    BigDecimal.ONE.subtract(
                            discount.divide(
                                    BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                    )
            );
            BigDecimal totalPrice = client.ordersWeight().multiply(pricePerUnit);

            result.add(new OrderReport(
                    client.companyName(),
                    totalPrice
            ));
            discount = decreaseDiscount(discount, discountStep);
        }
        return result;

    }

    private BigDecimal decreaseDiscount(BigDecimal discount, BigDecimal discountStep) {
        discount = discount.subtract(discountStep);
        return discount.max(BigDecimal.ZERO).min(new BigDecimal("100"));
    }
}
