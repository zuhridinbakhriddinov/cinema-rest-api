package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcinemarest.service.AfishaService;
import uz.pdp.appcinemarest.utill.Constants;

// Zuhridin Bakhriddinov 3/17/2022 10:35 AM
@RequestMapping("/afisha")
@RestController
public class AfishaController {
    @Autowired
    AfishaService afishaService;


    @GetMapping
    public HttpEntity getAllAfisha(
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "start_date") String sort
    ) {
        return afishaService.getAllAfisha(page, size, search, sort, true);
    }

}
