package org.example;

import org.example.config.AppConfig;
import org.example.config.OrderConfig;
import org.example.data.ClientOrder;
import org.example.data.OrderReport;
import org.example.services.files.FileOrderService;
import org.example.services.files.FileService;
import org.example.services.orders.OrderDiscountService;
import org.example.services.orders.OrderManager;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderManagerTest {

    @Test
    void createOrder() throws IOException {

        OrderConfig orderConfig = new OrderConfig(
                AppConfig.get("test.file.paths"),
                AppConfig.getDouble("test.base.cost"),
                AppConfig.getInteger("test.start.discount"),
                AppConfig.getInteger("test.discount.step")
        );

        FileService fileService = mock(FileOrderService.class);
        OrderDiscountService  orderDiscountService = mock(OrderDiscountService.class);
        ClientOrder clientOrder = mock(ClientOrder.class);
        OrderReport orderReport = mock(OrderReport.class);

        when(fileService.read("test.txt"))
                .thenReturn(List.of(clientOrder));

        when(orderDiscountService.calculate(anyList(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(List.of(orderReport));

        OrderManager manager = new OrderManager(fileService, orderDiscountService);


        manager.process(orderConfig);

        InOrder  inOrder = inOrder(fileService, orderDiscountService);

        inOrder.verify(fileService).read("test.txt");
        inOrder.verify(orderDiscountService).calculate(
                eq(List.of(clientOrder)),
                eq(AppConfig.getDouble("test.base.cost")),
                eq(AppConfig.getInteger("test.start.discount")),
                eq(AppConfig.getInteger("test.discount.step"))
        );
        inOrder.verify(fileService).write("test.txt", List.of(orderReport));
    }



}