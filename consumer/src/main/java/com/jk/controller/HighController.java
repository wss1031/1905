package com.jk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jk.service.HighService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Highcharts")
public class HighController {
    @Reference
    private HighService highService;

    @RequestMapping("queryCarPie")
    public List<Map<String, Object>> queryCarPie(){
        //查询数据库数据
        List<Map<String,Object>> map1 =highService.queryCar();
        //前台展示的返回的数据
        List<Map<String,Object>> map2 =new ArrayList<Map<String,Object>>();

        for (Map<String,Object> map:map1) {
            Map<String,Object> map3=new HashMap<>();
            map3.put("y",map.get("count"));
            map3.put("name",map.get("time"));
            map2.add(map3);
        }





        return map2;
    }




    @RequestMapping("queryCarChart")
    public List<Map<String, Object>> queryCarChart(){
        //查询数据库数据
        List<Map<String,Object>> map1 =highService.queryCarChart();
        //前台展示的返回的数据
        List<Map<String,Object>> map2 =new ArrayList<Map<String,Object>>();

        for (Map<String,Object> map:map1) {
            Map<String,Object> map3=new HashMap<>();
            map3.put("count",map.get("count"));
            map3.put("time",map.get("time"));
            map2.add(map3);
        }





        return map2;
    }


    @RequestMapping("queryCarMian")
    public List<Map<String, Object>> queryCarMian(){
        //查询数据库数据
        List<Map<String,Object>> map1 =highService.queryCarMian();
        //前台展示的返回的数据
        List<Map<String,Object>> map2 =new ArrayList<Map<String,Object>>();

        for (Map<String,Object> map:map1) {
            Map<String,Object> map3=new HashMap<>();
            map3.put("name",map.get("name"));
            map3.put("sum",map.get("sum"));
            map2.add(map3);
        }
        return map2;
    }
}
