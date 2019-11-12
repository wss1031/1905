package com.jk.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface HighDao {

    @Select("SELECT COUNT(DATE_FORMAT(carTime, '%Y-%M')) count,DATE_FORMAT(carTime, '%Y-%c') time\n" +
            "FROM t_car\n" +
            "WHERE DATE_FORMAT(carTime, '%Y-%c') >= '2019-01'\n" +
            "GROUP BY DATE_FORMAT(carTime, '%Y-%M')")
    List<Map<String, Object>> queryCar();
    @Select("SELECT  COUNT(DATE_FORMAT(carTime, '%u')) count,DATE_FORMAT(carTime, '%u') time\n" +
        "FROM t_car\n" +
        "WHERE DATE_FORMAT(carTime,'%u') \n" +
        "GROUP BY DATE_FORMAT(carTime, '%u')")
    List<Map<String, Object>> queryCarChart();
    @Select("select  t.carType name,sum(t.carSales) sum from t_car t GROUP BY t.carType")
    List<Map<String, Object>> queryCarMian();
}
