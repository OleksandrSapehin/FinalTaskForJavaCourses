package com.example.firstsecurityapp.controllers;


import com.example.firstsecurityapp.models.Person;
import com.example.firstsecurityapp.services.AdminService;
import com.example.firstsecurityapp.services.PersonService;
import com.example.firstsecurityapp.util.PurchaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AdminController {
    private final PersonService personService;
    private final AdminService adminService;
    @Autowired
    public AdminController(PersonService personService, AdminService adminService) {
        this.personService = personService;
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        adminService.doAdminStuff();

        List<Person> purchaseInfoList = personService.getAllPurchaseInfo();

        model.addAttribute("purchaseInfoList", purchaseInfoList);

        return "admin";
    }

}
