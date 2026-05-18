package org.example.services;

import org.example.data.ClientOrder;
import org.example.data.OrderReport;
import org.example.exceptions.DiscountException;
import org.example.services.orders.OrderDiscountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDiscountServiceTest {
    ClientOrder clientOrders = new ClientOrder(
            LocalDateTime.now(),
            "testCompanyName",
            new BigDecimal("1")
    );
    List<ClientOrder> orders = new ArrayList<>(Collections.singleton(clientOrders));

    BigDecimal cost = new BigDecimal("100");
    BigDecimal discount = new BigDecimal("50");
    BigDecimal discountStep = new BigDecimal("10");

    static Stream<Arguments> orderReportsNormalSorted() {
        List<ClientOrder> orderReports = IntStream.range(0, 10)
                .mapToObj(
                        i -> new ClientOrder(
                                LocalDateTime.of(2024, 1, 1, 10, 0).plusDays(i),
                                "CompanyName" + i,
                                new BigDecimal(i)
                        )
                ).toList();

        return Stream.of(
                Arguments.of(orderReports));
    }

    static Stream<Arguments> orderReportsForSortedTest() {
        List<LocalDateTime> dates= List.of(
                LocalDateTime.of(2024, 1, 3, 10, 0),
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 4, 10, 0),
                LocalDateTime.of(2024, 1, 5, 10, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0)
        );

        List<ClientOrder> orders =
                IntStream.range(0, dates.size())
                        .mapToObj(i ->
                            new ClientOrder(
                                    dates.get(i),
                                    "name" + i,
                                    BigDecimal.ONE
                            )
                        )
                        .toList();

        List<String> expectedOrders = List.of("name1", "name4", "name0", "name2", "name3");

        return Stream.of(
                Arguments.of(orders, expectedOrders)
        );
    }

    static Stream<BigDecimal> invalidDiscount() {
        return Stream.of(
                null,
                new BigDecimal("-0.01"),
                new BigDecimal("-1"),
                new BigDecimal("100.01"),
                new BigDecimal("150")
        );
    }

    static Stream<BigDecimal> validDiscount() {
        return Stream.of(
                new BigDecimal("0"),
                new BigDecimal("50"),
                new BigDecimal("100")
        );
    }

    //Проверка сортировки + правильность расчетов .calculate
    @ParameterizedTest
    @MethodSource("orderReportsNormalSorted")
    void shouldApplyDiscountCorrectly(List<ClientOrder> orders) {
        BigDecimal currentDiscount = new BigDecimal("50");
        List<OrderReport> result = returnOrderReports(orders);

        for(int i = 0; i < result.size(); i++) {
            BigDecimal pricePerUnit = cost.multiply(
                    BigDecimal.ONE.subtract(
                            currentDiscount.divide(
                                    BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                    )
            );
            BigDecimal expected = orders.get(i).ordersWeight().multiply(pricePerUnit);
            BigDecimal actual = result.get(i).totalPrice();

            assertEquals(0, expected.compareTo(actual));

            currentDiscount = decreaseDiscount(currentDiscount, discountStep);

        }
    }

    //проверка сортировки
    @ParameterizedTest
    @MethodSource("orderReportsForSortedTest")
    void sortedTest(List<ClientOrder> orders, List<String> expected) {
        List<OrderReport> result = returnOrderReports(orders);

        for(int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i).companyName(), expected.get(i));
        }
    }

    private List<OrderReport> returnOrderReports(List<ClientOrder> orders) {
        OrderDiscountService service = new OrderDiscountService();

        return service.calculate(
                orders,
                cost,
                discount,
                discountStep
        );
    }

    private BigDecimal decreaseDiscount(BigDecimal discount, BigDecimal discountStep) {
        discount = discount.subtract(discountStep);
        return discount.max(BigDecimal.ZERO).min(BigDecimal.valueOf(100));
    }

    @ParameterizedTest
    @MethodSource("invalidDiscount")
    void shouldDiscountIsInvalid(BigDecimal invalidDiscount) {
        List<ClientOrder> orders = List.of(new ClientOrder(
                LocalDateTime.of(2024, 1, 3, 10, 0),
                "name",
                BigDecimal.ONE
        ));

        OrderDiscountService service = new OrderDiscountService();

        assertThrows(
                DiscountException.class, () ->
                        service.calculate(
                                orders,
                                cost,
                                invalidDiscount,
                                discountStep
                        )
        );
    }

    @ParameterizedTest
    @MethodSource("validDiscount")
    void shouldDiscountIsValid(BigDecimal validDiscount) {
        List<ClientOrder> orders = List.of(new ClientOrder(
                LocalDateTime.of(2024, 1, 3, 10, 0),
                "name",
                BigDecimal.ONE
        ));

        OrderDiscountService service = new OrderDiscountService();

        assertDoesNotThrow(
                () ->
                        service.calculate(
                                orders,
                                cost,
                                validDiscount,
                                discountStep
                        )
        );
    }

    @Test
    void shouldReturnEmptyListWhenOrdersIsEmptyList() {
        OrderDiscountService service = new OrderDiscountService();

        List<OrderReport> result = service.calculate(
                Collections.emptyList(),
                new BigDecimal("100"),
                new BigDecimal("50"),
                new BigDecimal("5"));

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenOrdesIsNull() {
        OrderDiscountService service = new OrderDiscountService();

        List<OrderReport> resultNull = service.calculate(
                null,
                new BigDecimal("100"),
                new BigDecimal("50"),
                new BigDecimal("5"));

        assertTrue(resultNull.isEmpty());
    }
}
