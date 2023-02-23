package ru.clevertec.test.service.utils;

import java.io.*;
import java.nio.file.Path;

public class CheckPrinterAndWriter {


    public static void printCheck(StringBuilder builder, Path path) {
        File file = path.toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getOrder(Path path) {
        File file = path.toFile();
        String[] s = new String[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String read = reader.readLine();
            if (read != null) {
                s = read.split(" ");
            }
        } catch (IOException e) {
            System.out.println("No such order");
        }
        return s;
    }
}
