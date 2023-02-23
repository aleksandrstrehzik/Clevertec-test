package ru.clevertec.test.repository.factories;

import org.springframework.stereotype.Component;
import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.repository.entity.product.ProductBuilder;
import ru.clevertec.test.repository.entity.product.builders.*;

@Component
public class ProductFactory {

    public static final String NO_PRODUCT_WITH_ID = "No product with id=";

    public Product getProduct(Integer id) {
        Director director = new Director();
        ProductBuilder someProduct = null;
        switch (id) {
            case 1:
                someProduct = new BreadBuilder();
                break;
            case 2:
                someProduct = new BunBuilder();
                break;
            case 3:
                someProduct = new MilkBuilder();
                break;
            case 4:
                someProduct = new YogurtBuilder();
                break;
            default:
                System.out.println(NO_PRODUCT_WITH_ID + id);
                return null;
        }
        director.setProductBuilder(someProduct);
        return director.build();
    }
}
