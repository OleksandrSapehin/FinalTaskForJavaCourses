package com.example.firstsecurityapp.services;
import com.example.firstsecurityapp.models.Car;
import com.example.firstsecurityapp.models.Person;
import com.example.firstsecurityapp.repositories.PeopleRepository;
import com.example.firstsecurityapp.util.PurchaseInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PersonService {
   private final PeopleRepository peopleRepository;


    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public void removeCarFromPerson(int personId, int carId) {
        peopleRepository.removeCar(carId);
    }
    public Person getPersonById(int personId) {
        return peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public void save(Person person) {
        peopleRepository.save(person);
    }
    public List<Person> getAllPurchaseInfo() {
        return peopleRepository.findAllWithCars();
    }



}
