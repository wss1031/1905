package com.jk.mapper;

import com.jk.model.CarModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CarMapper {

    @Select("select * from t_car")
    List<CarModel> search();
    @Insert("insert into t_car (carName,carTime,carSales,carType) values (#{carModel.carName},#{carModel.carTime},#{carModel.carSales},#{carModel.carType})")
    void add(@Param("carModel") CarModel carModel);
}
