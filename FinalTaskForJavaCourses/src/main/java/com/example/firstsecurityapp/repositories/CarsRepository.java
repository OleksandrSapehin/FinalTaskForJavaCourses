package com.example.firstsecurityapp.repositories;

import com.example.firstsecurityapp.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Car,Integer>, JpaSpecificationExecutor<Car> {


}
