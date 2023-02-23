package ru.clevertec.test.service.impl.store;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.test.repository.dao.ProductRepository;
import ru.clevertec.test.repository.entity.Card;
import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.repository.factories.CardFactory;
import ru.clevertec.test.repository.factories.ProductFactory;
import ru.clevertec.test.service.exception.NullInput;
import ru.clevertec.test.service.impl.checks.CheckWithDiscountService;
import ru.clevertec.test.service.interfaces.checks.CheckService;
import ru.clevertec.test.service.interfaces.stores.StoreService;
import ru.clevertec.test.service.utils.CheckPrinterAndWriter;
import ru.clevertec.test.service.utils.CheckUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    private final ProductFactory factory;
    private final CheckService checkService;
    private final CardFactory cardFactory;
    private final ProductRepository productDAO;

    public StoreServiceImpl(ProductFactory factory, @Qualifier("Simple") CheckService checkService,
                            CardFactory cardFactory, ProductRepository productDAO) {
        this.factory = factory;
        this.checkService = checkService;
        this.cardFactory = cardFactory;
        this.productDAO = productDAO;
    }

    @Override
    public void createCheck(String[] d, Path path, boolean UseDatabase) {
        if (d.length != 0) {
            if (UseDatabase) {
                Map<Product, Integer> shoppingСart = putProductsInCartFromDatabase(d);
                saveInTxt(d, shoppingСart, path);
                printInConsole(d, shoppingСart);
            } else {
                Map<Product, Integer> shoppingСart = putProductsInCartByDirector(d);
                saveInTxt(d, shoppingСart, path);
                printInConsole(d, shoppingСart);
            }
        } else throw new NullInput();
    }

    @Override
    public void saveInTxt(String[] d, Map<Product, Integer> shoppingСart, Path path) {
        if (!shoppingСart.isEmpty()) {
            StringBuilder builder = getCompletedCheck(d, shoppingСart);
            CheckPrinterAndWriter.printCheck(builder, path);
        }
    }

    @Override
    public void printInConsole(String[] d, Map<Product, Integer> shoppingСart) {
        System.out.println(getCompletedCheck(d, shoppingСart));
    }

    @Override
    public Map<Product, Integer> putProductsInCartByDirector(String[] d) {
        int[] idArray = CheckUtil.getIdArray(d);
        int[] numArray = CheckUtil.getNumArray(d);
        Map<Product, Integer> shoppingСart = checkService.getShoppingСart();
        for (int i = 0; i < idArray.length; i++) {
            Product product = factory.getProduct(idArray[i]);
            if (product != null) {
                shoppingСart.put(product, numArray[i]);
            }
        }
        return shoppingСart;
    }

    @Override
    public Map<Product, Integer> putProductsInCartFromDatabase(String[] d) {
        int[] idArray = CheckUtil.getIdArray(d);
        int[] numArray = CheckUtil.getNumArray(d);
        Map<Product, Integer> shoppingСart = checkService.getShoppingСart();
        for (int i = 0; i < idArray.length; i++) {
            Optional<Product> byId = productDAO.findById(idArray[i]);
            if (byId.isPresent()) {
                shoppingСart.put(byId.get(), numArray[i]);
            }
        }
        return shoppingСart;
    }

    private StringBuilder getCompletedCheck(String[] d, Map<Product, Integer> shoppingСart) {
        StringBuilder builder = new StringBuilder();
        StringBuilder append = builder.append("                 CASH RECEIPT")
                .append("\n")
                .append("\n")
                .append("                 +203920392039023")
                .append("\n")
                .append("Date                              " + LocalDate.now())
                .append("\n")
                .append("Time                              " + LocalTime.now())
                .append("\n")
                .append("_____________________________________________________")
                .append("\n")
                .append("№   Description           Price     Total")
                .append("\n");
        for (Product product : shoppingСart.keySet()) {
            Integer value = shoppingСart.get(product);
            if (product.isIsOnDiscount() && shoppingСart.get(product) >= 5) {
                BigDecimal nominationCost = new CheckWithDiscountService(checkService).nominationCost(product, value).setScale(2, RoundingMode.CEILING);
                builder.append(value + "   " + product.getDescription() + "             "
                                + product.getPrice() + "          " + nominationCost)
                        .append("\n")
                        .append("             promotional item discount " + checkService.nominationCost(product, value).subtract(nominationCost).setScale(2, RoundingMode.CEILING))
                        .append("\n");
            } else builder.append(value + "   " + product.getDescription() + "             "
                            + product.getPrice() + "          " + checkService.nominationCost(product, value).setScale(2, RoundingMode.CEILING))
                    .append("\n");
        }
        builder.append("______________________________________________________")
                .append("\n")
                .append("Total cost                                   " + checkService.generalСost().setScale(2, RoundingMode.CEILING))
                .append("\n");
        if (CheckUtil.IsCardPresent(d)) {
            Card card = cardFactory.getCard(CheckUtil.getDiscountCard(d));
            builder.append("Discount amount                            " + checkService.discountAmount(card, checkService.generalСost()).setScale(2, RoundingMode.CEILING))
                    .append("\n")
                    .append("Discount price                             " + checkService.costWithDiscount(card, checkService.generalСost()).setScale(2, RoundingMode.FLOOR));
        }
        return append;
    }
}
