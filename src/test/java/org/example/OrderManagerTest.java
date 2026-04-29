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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderManagerTest {
    @Mock
    FileService fileService;
    @Mock
    OrderDiscountService  orderDiscountService;

    @InjectMocks
    OrderManager manager;

    @Test
    void createOrder() throws IOException {

        OrderConfig orderConfig = new OrderConfig(
                "test.txt,test",
                100,
                10,
                2
        );

        ClientOrder clientOrder = new ClientOrder(
                LocalDateTime.now(),
                "test-company",
                100);

        OrderReport orderReport = new OrderReport(
                "test-company",
                100
        );

        when(fileService.read("test.txt"))
                .thenReturn(List.of(clientOrder));

        when(orderDiscountService.calculate(anyList(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(List.of(orderReport));

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