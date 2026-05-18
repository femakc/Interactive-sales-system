package org.example.services.parsers;

import org.example.data.ClientOrder;
import org.example.exceptions.ParseException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientParser implements LineParser<ClientOrder> {

    @Override
    public ClientOrder parse(String line, String delimiter) {
        try {
            String[] parts = line.split(delimiter);

            if (parts.length < 3) {
                throw new ParseException("Invalid line: " + line);
            }

            LocalDateTime orderDate = LocalDateTime.parse(parts[0]);
            String companyName = parts[1];
            BigDecimal ordersWeight = new BigDecimal(parts[2]);

            return new ClientOrder(orderDate, companyName, ordersWeight);

        } catch (Exception e) {
            throw new ParseException("Ошибка в строке: " + line, e);
        }
    }
}
