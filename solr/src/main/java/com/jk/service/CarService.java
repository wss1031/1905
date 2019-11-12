package com.jk.service;

import com.jk.model.CarModel;

import java.util.List;

public interface CarService {
    List<CarModel> search();

    void add(CarModel carModel);
}
