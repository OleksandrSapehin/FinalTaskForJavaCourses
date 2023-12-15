package com.example.firstsecurityapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "year")
    private int year;
    @Column(name = "price")
    private double price;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "tech_specification")
    private String techSpecification;
    @Column(name = "tech_condition")
    private String techCondition;
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person owner;
    public void  generateImagePath(){
        this.imagePath = "/images/car-" + getId() + ".png";
    }
}
