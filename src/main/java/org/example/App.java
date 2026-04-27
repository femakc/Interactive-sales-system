package org.example;

import org.example.data.ClientOrder;
import org.example.services.FileOrderService;
import org.example.services.OrderDiscountService;
import org.example.services.OrderManager;

public class App
{
    public static void main( String[] args )
    {
        String paths = AppConfig.get("files.paths");
        double cost = AppConfig.getDouble("base.cost");
        int discount = AppConfig.getInteger("start.discount");
        int discountStep = AppConfig.getInteger("discount.step");
        FileOrderService fileOrderService = new FileOrderService();
        OrderDiscountService orderDiscountService = new OrderDiscountService();
//        ClientOrder clientOrder = new ClientOrder();

        OrderManager orderManager = new OrderManager(
                paths,
                cost,
                discount,
                discountStep,
                fileOrderService,
                orderDiscountService
//                clientOrder
        );
        orderManager.process();
    }
}
