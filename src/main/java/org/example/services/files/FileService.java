package org.example.services.files;

import org.example.data.ClientOrder;
import org.example.data.OrderReport;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<ClientOrder> read(String filePath) throws IOException;

    void write(String path, List<OrderReport> clients);

}
