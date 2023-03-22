package ru.clevertec.test.service.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CheckPrinterAndWriterTest {

    public static final String STRING_BUILDER1 = "StringBuilder";
    private final Path path = Path.of("src", "test", "resources", "test.txt");
    private StringBuilder stringBuilder;

    @BeforeEach
    void setUp() throws IOException {
        new File(String.valueOf(path)).createNewFile();
        stringBuilder = new StringBuilder(STRING_BUILDER1);
    }
    @Test
    void printCheck() {
        CheckPrinterAndWriter.printCheck(stringBuilder, path);
        assertThat(CheckPrinterAndWriter.getOrder(path)).isNotEmpty();
    }

    @Test
    void getOrder() {
        assertThat(CheckPrinterAndWriter.getOrder(path)).isEmpty();
    }


    @AfterEach
    void tearDown() {
        new File(String.valueOf(path)).delete();
    }
}