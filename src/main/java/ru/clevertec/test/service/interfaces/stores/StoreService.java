package ru.clevertec.test.service.interfaces.stores;

import ru.clevertec.test.repository.entity.product.Product;

import java.nio.file.Path;
import java.util.Map;

public interface StoreService {
    void createCheck(String[] d, Path path, boolean UseDatabase);

    void saveInTxt(String[] d, Map<Product, Integer> shoppingСart, Path path);

    void printInConsole(String[] d, Map<Product, Integer> shoppingСart);

    Map<Product, Integer> putProductsInCartByDirector(String[] d);

    Map<Product, Integer> putProductsInCartFromDatabase(String[] d);
}
