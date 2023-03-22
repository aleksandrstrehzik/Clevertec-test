package ru.clevertec.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.clevertec.test.service.impl.PrintPdfServiceImpl;
import ru.clevertec.test.service.interfaces.stores.StoreService;

@RequiredArgsConstructor
@Controller
public class StoreController {

    private final StoreService storeService;
    private final PrintPdfServiceImpl pdfService;

    @GetMapping("/printpdf")
    public String printpdf() {
        pdfService.printPdf();
        return "redirect:/";
    }

    @GetMapping("/printInConsole")
    public String printInConsole() {
        storeService.printInConsole();
        return "redirect:/";
    }
}
