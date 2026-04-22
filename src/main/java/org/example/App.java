package org.example;

import org.example.data.ClientOrder;
import org.example.services.DiscountCounter;
import org.example.services.Reader;
import org.example.services.Writer;
import org.example.services.parsers.ClientParser;
import org.example.services.utilites.buildResultFileName;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        String paths = AppConfig.get("files.paths");

        List<String> filePaths = Arrays.stream(paths.split(","))
                .map(String::trim)
                .toList();

        DiscountCounter discountCounter = new DiscountCounter();

        filePaths.stream()
                .filter(path -> !path.isBlank())
                .forEach(path -> {

                    List<ClientOrder> clients =
                            null;
                    try {
                        clients = Reader.read(path, new ClientParser());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    discountCounter.calculation(clients);
                    String resultPath = buildResultFileName.filePath(path);
                    Writer.write(resultPath, clients);
                });
    }
}
