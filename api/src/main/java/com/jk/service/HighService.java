package com.jk.service;

import java.util.List;
import java.util.Map;

public interface HighService {
    List<Map<String, Object>> queryCar();

    List<Map<String, Object>> queryCarChart();

    List<Map<String, Object>> queryCarMian();
}
