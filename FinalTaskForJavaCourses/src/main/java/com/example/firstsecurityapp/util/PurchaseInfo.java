package com.example.firstsecurityapp.util;

import com.example.firstsecurityapp.models.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseInfo {
    private int personId;
    private String fullName;
    private String userName;
    private String address;
    private String phoneNumber;
    private List<Car> cars;
}
