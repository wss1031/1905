package com.jk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jk.mapper.HighDao;
import com.jk.service.HighService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Service
@Component
public class HighServiceImpl implements HighService {

    @Autowired
    private HighDao highDao;


    @Override
    public List<Map<String, Object>> queryCar() {

        return highDao.queryCar();
    }

    @Override
    public List<Map<String, Object>> queryCarChart() {
        return highDao.queryCarChart();
}

    @Override
    public List<Map<String, Object>> queryCarMian() {
        return highDao.queryCarMian();
    }
}
