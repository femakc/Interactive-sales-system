package org.example.services;

import org.example.data.ClientOrder;
import org.example.exceptions.FileReadException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OrderManager {

    private final String paths;
    private final double cost;
    private final int discount;
    private final int discountStep;

    private final FileOrderService fileOrderService;
    private final OrderDiscountService orderDiscountService;

    public OrderManager(
            String paths,
            double cost,
            int discount,
            int discountStep,
            FileOrderService fileOrderService,
            OrderDiscountService orderDiscountService
    ) {
        this.paths = paths;
        this.cost = cost;
        this.discount = discount;
        this.discountStep = discountStep;
        this.fileOrderService = fileOrderService;
        this.orderDiscountService = orderDiscountService;
    }

    public void process() {

        List<String> filePaths = Arrays.stream(paths.split(","))
                .map(String::trim)
                .toList();

        filePaths.stream()
                .filter(path -> !path.isBlank())
                .forEach(path -> {

                    List<ClientOrder> clients;
                    try {
                        clients = fileOrderService.read(path);
                    } catch (IOException e) {
                        throw new FileReadException("Ошибка чтения файла", e);
                    }
                    fileOrderService.write(
                            path,
                            orderDiscountService.calculate(
                                    clients,
                                    cost,
                                    discount,
                                    discountStep));
                });
    }
}
