package com.example.firstsecurityapp.services;

import com.example.firstsecurityapp.models.Car;
import com.example.firstsecurityapp.models.Person;
import com.example.firstsecurityapp.repositories.PeopleRepository;
import com.example.firstsecurityapp.security.PersonDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService  {
    private final CarService carService;
    private final PeopleRepository peopleRepository;

    public PersonDetailsService(CarService carService, PeopleRepository peopleRepository) {
        this.carService = carService;
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Person> person = peopleRepository.findByUsername(username);

       if (person.isEmpty())
           throw new UsernameNotFoundException("User not found!");

       return new PersonDetails(person.get());
    }
    public List<Car> getCarsByPersonId(int personId) {
        Optional<Person> optionalPerson = peopleRepository.findById(personId);

        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            return person.getCars();
        } else {

            return Collections.emptyList();
        }
    }
    public Person getAuthPerson(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }



   }

