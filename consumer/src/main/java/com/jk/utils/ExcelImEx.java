package com.jk.utils;


import com.jk.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

/**
 * author：wdd
 * create time:2019/10/15
 * email：
 * tel：
 */
public class ExcelImEx {

    public static void exportExcel(HttpServletResponse response, String filename,List<Object> list,String[] titles,String sheetName,Class bean) throws IOException, IllegalAccessException {
        list = (List<Object>) list.get(0);
        boolean xls = ExcelUtils.isXls(filename);
        //创建excel工作薄
        Workbook workBook = null;
        if(xls){//.xls  03
            workBook = new HSSFWorkbook();
        }else{//.xlsx  07
            workBook = new XSSFWorkbook();
        }
        //创建sheet页
        Sheet sheet = workBook.createSheet(sheetName);
        //String[] titles = {"id","用户名","密码","角色"};
        //创建行：标题
        Row row1 = sheet.createRow(0);
        for (int i = 0; i <titles.length ; i++) {
            row1.createCell(i).setCellValue(titles[i]);
        }

        //获取类的属性数值：用java反射机制
        Field[] fields = bean.getDeclaredFields();
        for (int i = 0; i < list.size(); i++) {
            Object user = list.get(i);
            //创建行
            Row row = sheet.createRow(i+1);
            for (int j = 0; j <fields.length ; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                Object o = field.get(user);
                if(o!=null){
                    row.createCell(j).setCellValue(o.toString());
                }
            }
/*            //创建列
            Cell cell = row.createCell(0);
            //给cell赋值
            cell.setCellValue(user.getId());

            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getPassword());
            row.createCell(3).setCellValue(user.getRoleName());*/
        }

        //设置头信息
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename,"utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workBook.write(outputStream);
        outputStream.close();//关闭流
    }
}
