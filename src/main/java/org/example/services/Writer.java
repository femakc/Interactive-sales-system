package org.example.services;

import org.example.data.ClientOrder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Writer {

    public static void write(String resultPath, List<ClientOrder> clients) {
        System.out.println("Writing to: " + resultPath);

        List<String> lines = clients.stream()
                .map(client -> client.getCompanyName() + " " + client.getOrdersPrice())
                .toList();

        try {
            Files.write(Path.of(resultPath), lines);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи файла: " + resultPath, e);
        }
    }
}
