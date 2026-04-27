package org.example.services.files;

import org.example.config.AppConfig;
import org.example.data.ClientOrder;
import org.example.data.OrderReport;
import org.example.exceptions.FileReadException;
import org.example.exceptions.FileWriteException;
import org.example.services.parsers.ClientParser;
import org.example.services.utilites.DelimiterUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileOrderService implements FileService {

    @Override
    public List<ClientOrder> read(String filePath) throws IOException {
        File file = new File(filePath);
        String delimiter = DelimiterUtils.getDelimiter(getExtension(file.getName()));
        ClientParser clientParser = new ClientParser();

        if (!file.exists()) {
            throw new IOException("File not found");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines()
                    .map(line -> clientParser.parse(line, delimiter))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileReadException("Ошибка чтения строки в файле", e);
        }
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex <= 0) {
            return "";
        }

        return fileName.substring(dotIndex + 1);
    }

    @Override
    public void write(String path, List<OrderReport> clients) {
        String newFileName = buildResultFileName(path);
        System.out.println("Writing to: " + newFileName);

        List<String> lines = clients.stream()
                .map(client -> client.companyName() + " " + client.totalPrice())
                .toList();

        try {
            Files.write(Path.of(newFileName), lines);
        } catch (IOException e) {
            throw new FileWriteException("Ошибка записи файла: " + newFileName, e);
        }
    }

    private String buildResultFileName(String path) {
        String prefix = AppConfig.get("result.file.prefix");

        int dotIndex = path.lastIndexOf(".");

        if (dotIndex == -1) {
            return path + prefix;
        }

        String name = path.substring(0, dotIndex);
        String extension = path.substring(dotIndex);

        return name + prefix + extension;
    }
}
