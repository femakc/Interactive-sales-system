package org.example.services.parsers;

import org.example.data.ClientOrder;

import java.time.LocalDateTime;

import static org.example.services.utilites.DelimiterUtils.getDelimiter;

public class ClientParser implements ILineParser<ClientOrder> {

    @Override
    public ClientOrder parse(String line) {
        try {
            String[] parts = line.split(getDelimiter(line));

            if (parts.length < 3) {
                throw new RuntimeException("Invalid line: " + line);
            }

            LocalDateTime orderDate = LocalDateTime.parse(parts[0]);
            String companyName = parts[1];
            float ordersWeight = Float.parseFloat(parts[2]);

            return new ClientOrder(orderDate, companyName, ordersWeight);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка в строке: " + line, e);
        }
    }

}
