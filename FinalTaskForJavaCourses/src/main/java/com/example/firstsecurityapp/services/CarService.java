package com.example.firstsecurityapp.services;

import com.example.firstsecurityapp.models.Car;
import com.example.firstsecurityapp.models.Person;
import com.example.firstsecurityapp.repositories.CarsRepository;
import com.example.firstsecurityapp.repositories.PeopleRepository;
import com.example.firstsecurityapp.util.CarFilterForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarsRepository carsRepository;
    private final PeopleRepository peopleRepository;

    private final PersonService personService;

    @Value("${car.images.path}")
    private String imagesPath;

    public CarService(CarsRepository carsRepository, PeopleRepository peopleRepository, PersonService personService) {
        this.carsRepository = carsRepository;
        this.peopleRepository = peopleRepository;

        this.personService = personService;
    }
    public List<Car> findAllCars(){
        return carsRepository.findAll();
   }
   public Car findOne(int id){
        Optional<Car> foundCar = carsRepository.findById(id);
        return foundCar.orElse(null);
   }
   public void generateImagePath(){
       List<Car> cars = findAllCars();
       for (Car car : cars) {
           car.generateImagePath();
           carsRepository.save(car);
       }
   }
    public List<Car> filterCars(CarFilterForm filterForm) {
        Specification<Car> specification = Specification.where(null);

        if (filterForm.getTechSpec() != null && !filterForm.getTechSpec().isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("techSpecification")),
                            "%" + filterForm.getTechSpec().toLowerCase() + "%"));
        }

        if (filterForm.getTechCondition() != null && !filterForm.getTechCondition().isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("techCondition"), filterForm.getTechCondition()));
        }

        if (filterForm.getPrice() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), filterForm.getPrice()));
        }

        if (filterForm.getBrand() != null && !filterForm.getBrand().isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")),
                            "%" + filterForm.getBrand().toLowerCase() + "%"));
        }

        return carsRepository.findAll(specification);
    }
   public void associatePersonWithCar(int personId, int carId){
       Person person = peopleRepository.findById(personId).orElse(null);
       Car car = carsRepository.findById(carId).orElse(null);
       if (person != null && car != null) {
           car.setOwner(person);
           carsRepository.save(car);
       }

   }
    public void removeCarFromPerson(int personId, int carId) {
        Person person = peopleRepository.findById(personId).orElse(null);
        Car car = carsRepository.findById(carId).orElse(null);

        if (!person.getCars().contains(car)) {
            throw new RuntimeException("Car does not belong to the specified person");
        }

        personService.removeCarFromPerson(personId,carId);
        carsRepository.save(car);
    }




}
