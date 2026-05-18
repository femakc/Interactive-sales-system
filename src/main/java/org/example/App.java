package org.example;

import org.example.config.AppConfig;
import org.example.services.files.FileOrderService;
import org.example.config.OrderConfig;
import org.example.services.orders.OrderDiscountService;
import org.example.services.orders.OrderManager;

public class App
{
    public static void main( String[] args )
    {
        OrderConfig orderConfig = new OrderConfig(
            AppConfig.get("files.paths"),
            AppConfig.getBigDecimal("base.cost"),
            AppConfig.getBigDecimal("start.discount"),
            AppConfig.getBigDecimal("discount.step")
        );

        FileOrderService fileOrderService = new FileOrderService();
        OrderDiscountService orderDiscountService = new OrderDiscountService();

        OrderManager orderManager = new OrderManager(
                fileOrderService,
                orderDiscountService
        );
        orderManager.process(orderConfig);
    }
}
