package com.example.firstsecurityapp.controllers;
import com.example.firstsecurityapp.models.Car;
import com.example.firstsecurityapp.models.Person;
import com.example.firstsecurityapp.services.PersonService;
import com.example.firstsecurityapp.util.PurchaseInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class PersonController {
    private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;

    }
    @PostMapping("/complete-purchase")
    public String completePurchase(@RequestParam int personId,
                                   @RequestParam String fullName,
                                   @RequestParam String userName,
                                   @RequestParam String address,
                                   @RequestParam List<Car> cars) {
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        purchaseInfo.setPersonId(personId);
        purchaseInfo.setFullName(fullName);
        purchaseInfo.setUserName(userName);
        purchaseInfo.setAddress(address);
        purchaseInfo.setCars(cars);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String purchaseInfoJson = objectMapper.writeValueAsString(purchaseInfo);

            Person person = personService.getPersonById(personId);

            person.setPurchaseInfo(purchaseInfoJson);

            personService.save(person);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";

    }


}
