package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcinemarest.service.AdminDashboardService;
import uz.pdp.appcinemarest.utill.Constants;

// Zuhridin Bakhriddinov 3/29/2022 9:27 AM
@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    AdminDashboardService adminDashboardService;

  /*  @GetMapping("dashboard")
    public HttpEntity getAllMovieSessions() {
        *//*return adminDashboardService.getStatistics();*//*

    }*/


}
