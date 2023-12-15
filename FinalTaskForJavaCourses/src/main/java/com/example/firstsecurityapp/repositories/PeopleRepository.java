package com.example.firstsecurityapp.repositories;

import com.example.firstsecurityapp.models.Car;
import com.example.firstsecurityapp.models.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByUsername(String username);
    @Modifying
    @Transactional
    @Query("UPDATE Car c SET c.owner = null WHERE c.id = :carId")
    void removeCar(@Param("carId") int carId);

    @Query("SELECT DISTINCT p FROM Person p JOIN FETCH p.cars c")
    List<Person> findAllWithCars();
}
