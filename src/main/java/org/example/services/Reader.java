package org.example.services;

import org.example.data.ClientOrder;
import org.example.services.parsers.ILineParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Reader {

    public static List<ClientOrder> read(String filePath, ILineParser<ClientOrder> parser) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("File not found");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines()
                    .map(parser::parse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}