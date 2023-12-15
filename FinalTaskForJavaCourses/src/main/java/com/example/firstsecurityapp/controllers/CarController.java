package com.example.firstsecurityapp.controllers;

import com.example.firstsecurityapp.models.Car;
import com.example.firstsecurityapp.models.Person;
import com.example.firstsecurityapp.security.PersonDetails;
import com.example.firstsecurityapp.util.CarFilterForm;
import com.example.firstsecurityapp.services.CarService;
import com.example.firstsecurityapp.services.PersonDetailsService;
import com.example.firstsecurityapp.util.PurchaseInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public CarController(CarService carService, PersonDetailsService personDetailsService) {
        this.carService = carService;

        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/all")
    public String showCars(Model model){
        carService.generateImagePath();
        List<Car> cars = carService.findAllCars();
        model.addAttribute("cars",cars);
        model.addAttribute("filterForm", new CarFilterForm());

        return "cars/main";

    }
    @GetMapping("/filter")
    public String filterCars(@ModelAttribute("filterForm") CarFilterForm filterForm, Model model) {
        List<Car> filteredCars = carService.filterCars(filterForm);
        model.addAttribute("cars", filteredCars);

        return "cars/main";
    }

    @PostMapping("/buy")
    public String associatePersonWithCar(
            @RequestParam int carId, Model model) {
        Person person = personDetailsService.getAuthPerson();
        int id =  person.getId();
        model.addAttribute("personId",id);
        model.addAttribute("carId", carId);
        carService.associatePersonWithCar(id, carId);
        return "redirect:access";

        }


    @GetMapping("/access")
        public String showBuyInfo(Model model){
          Person person =  personDetailsService.getAuthPerson();
           int id =  person.getId();
           List<Car> cars = personDetailsService.getCarsByPersonId(id);
        model.addAttribute("cars",cars);
           model.addAttribute("person",person);
           return "cars/buySuccess";
       }
    @PostMapping("/remove")
    public String removeCarFromPerson(
            @RequestParam int carId, Model model) {
        Person person = personDetailsService.getAuthPerson();
        int personId = person.getId();
        carService.removeCarFromPerson(personId, carId);
        return "redirect:/car/access";
    }






}
