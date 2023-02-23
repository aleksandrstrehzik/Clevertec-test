package ru.clevertec.test;

import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.repository.entity.product.builders.Director;
import ru.clevertec.test.repository.factories.CardFactory;
import ru.clevertec.test.repository.factories.ProductFactory;
import ru.clevertec.test.service.impl.checks.SimpleCheckService;
import ru.clevertec.test.service.impl.store.StoreServiceImpl;
import ru.clevertec.test.service.interfaces.stores.StoreService;
import ru.clevertec.test.service.utils.CheckPrinterAndWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

public class CheckRunner {
    public static void main(String[] args) throws SQLException, IOException {
      /*  Path order = Path.of("src", "main", "resources", "check", "order.txt");
        Path printCheck = Path.of("src", "main", "resources", "check", "check.txt");
        String[] stringOrder = CheckPrinterAndWriter.getOrder(order);
        StoreService storeService = new StoreServiceImpl(new ProductFactory(),
                new SimpleCheckService(), new CardFactory(), );
        storeService.createCheck(stringOrder, printCheck, true);
        Director director = new Director();*/
        HashMap<Product, Integer> productIntegerHashMap = new HashMap<>();
        Product product = new ProductFactory().getProduct(1);
        productIntegerHashMap.put(product, 4);
        Set<Product> products = productIntegerHashMap.keySet();
    }
}
