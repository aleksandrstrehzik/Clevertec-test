package ru.clevertec.test.service.impl.store;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.test.repository.dao.ProductRepository;
import ru.clevertec.test.repository.entity.Card;
import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.repository.factories.CardFactory;
import ru.clevertec.test.repository.factories.ProductFactory;
import ru.clevertec.test.service.data.CardBuilder;
import ru.clevertec.test.service.data.ProductBuilder;
import ru.clevertec.test.service.exception.NullInput;
import ru.clevertec.test.service.impl.checks.SimpleCheckService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {

    @Mock
    private ProductFactory productFactory;

    @Spy
    private SimpleCheckService checkService;

    @Mock
    private CardFactory cardFactory;

    @Mock
    private ProductRepository productDAO;

    @InjectMocks
    private StoreServiceImpl storeService;

    private final Path order = Path.of("src", "test", "resources", "order.txt");
    private final String[] orderWithCard = {"1-3", "3-4", "4-7", "Card-1234"};
    private final String[] orderWithoutCard = {"1-3", "2-2", "4-9"};
    private final Product product1 = ProductBuilder.aProduct().id(1).build();
    private final Product product2 = ProductBuilder.aProduct().id(2).build();
    private final Product product3 = ProductBuilder.aProduct().id(3).build();
    private final Product product4 = ProductBuilder.aProduct().id(4).build();
    public final Card card = CardBuilder.aCard().discount("10").build();

    @BeforeEach
    void setUp() throws IOException {
        new File(String.valueOf(order)).createNewFile();
    }

    @Nested
    class CreateCheck {
        @Test
        void checkCreateCheckUseDatabaseWithoutCard() {
            Mockito.doReturn(Optional.of(product1)).
                    when(productDAO).findById(1);
            Mockito.doReturn(Optional.of(product3)).
                    when(productDAO).findById(2);
            Mockito.doReturn(Optional.of(product4)).
                    when(productDAO).findById(4);

            storeService.createCheck(orderWithoutCard, order, true);

            Mockito.verify(productDAO).findById(1);
            Mockito.verify(productDAO).findById(2);
            Mockito.verify(productDAO).findById(4);

            assertThat(new File(String.valueOf(order))).isNotEmpty();
        }

        @Test
        void checkCreateCheckUseDatabaseWithCard() {
            Mockito.doReturn(Optional.of(product1)).
                    when(productDAO).findById(1);
            Mockito.doReturn(Optional.of(product3)).
                    when(productDAO).findById(3);
            Mockito.doReturn(Optional.of(product4)).
                    when(productDAO).findById(4);
            Mockito.doReturn(card)
                    .when(cardFactory).getCard("Card-1234");

            storeService.createCheck(orderWithCard, order, true);

            Mockito.verify(productDAO).findById(1);
            Mockito.verify(productDAO).findById(3);
            Mockito.verify(productDAO).findById(4);

            assertThat(new File(String.valueOf(order))).isNotEmpty();
        }

        @Test
        void checkCreateCheckUseDatabaseShouldThrowNullInput() {
            assertThrows(NullInput.class,
                    () -> storeService.createCheck(new String[]{""}, order, true));
        }
    }

    @Test
    void checkSaveInTxtMapNotEmpty() {
        Map<Product, Integer> map = new HashMap<>();
        Product product1 = ProductBuilder.aProduct().id(1).build();
        map.put(product1, 7);
        storeService.saveInTxt(orderWithoutCard, map, order);

        assertThat(new File(String.valueOf(order))).isNotEmpty();
    }

    @Test
    void checkSaveInTxtMapEmpty() {
        Map<Product, Integer> map = new HashMap<>();
        storeService.saveInTxt(orderWithoutCard, map, order);

        assertThat(new File(String.valueOf(order))).isEmpty();
    }

    @Test
    void printInConsole() {
        Map<Product, Integer> map = new HashMap<>();
        map.put(product1, 7);
        storeService.printInConsole(orderWithoutCard, map);

        Mockito.verify(checkService).getShoppingСart();

        assertTrue(true);
    }

    @Test
    void CheckPutProductsInCartByDirector() {
        Mockito.doReturn(product1)
                .when(productFactory).getProduct(1);
        Mockito.doReturn(product2)
                .when(productFactory).getProduct(2);
        Mockito.doReturn(product4)
                .when(productFactory).getProduct(4);

        Map<Product, Integer> map = storeService.putProductsInCartByDirector(orderWithoutCard);

        assertThat(map)
                .containsKeys(product1, product2, product4);
    }

    @Test
    void CheckPutProductsInCartFromDatabase() {
        Mockito.doReturn(Optional.of(product1)).
                when(productDAO).findById(1);
        Mockito.doReturn(Optional.of(product2)).
                when(productDAO).findById(2);
        Mockito.doReturn(Optional.of(product4)).
                when(productDAO).findById(4);

        Map<Product, Integer> map = storeService.putProductsInCartFromDatabase(orderWithoutCard);

        assertThat(map)
                .containsKeys(product1, product2, product4);
    }

    @AfterEach
    void tearDown() {
        new File(String.valueOf(order)).delete();
    }
}