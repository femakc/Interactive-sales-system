package org.example.services;

import org.example.AppConfig;
import org.example.data.ClientOrder;
import org.example.services.utilites.FileOrderService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OrderManager {

    public static void process() {
        String paths = AppConfig.get("files.paths");
        double cost = AppConfig.getDouble("base.cost");

        List<String> filePaths = Arrays.stream(paths.split(","))
                .map(String::trim)
                .toList();

        filePaths.stream()
                .filter(path -> !path.isBlank())
                .forEach(path -> {

                    List<ClientOrder> clients;
                    try {
                        clients = FileOrderService.read(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    FileOrderService.write(path, new OrderDiscountService().calculate(clients, cost));
                });
    }
}
