package com.jk.service.Impl;

import com.jk.mapper.CarMapper;
import com.jk.model.CarModel;
import com.jk.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Override
    public List<CarModel> search() {
        return carMapper.search();
    }

    @Override
    public void add(CarModel carModel) {
        carMapper.add(carModel);
    }
}
