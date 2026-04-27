package org.example.services.orders;

import org.example.config.OrderConfig;
import org.example.data.ClientOrder;
import org.example.exceptions.FileReadException;
import org.example.services.files.FileOrderService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OrderManager {

    private final FileOrderService fileOrderService;
    private final OrderDiscountService orderDiscountService;

    public OrderManager(
            FileOrderService fileOrderService,
            OrderDiscountService orderDiscountService
    ) {
        this.fileOrderService = fileOrderService;
        this.orderDiscountService = orderDiscountService;
    }

    public void process(OrderConfig orderConfig) {
        String paths = orderConfig.paths();
        double cost = orderConfig.cost();
        int discount = orderConfig.discount();
        int discountStep = orderConfig.discountStep();

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
