package com.jk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jk.model.TreeModel;
import com.jk.model.UserModel;
import com.jk.service.UserService;
import com.jk.utils.ExcelImEx;
import com.jk.utils.ExcelUtils;
import com.jk.utils.ExportExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {
    @Reference
    private UserService userService;

    //解决dubbo分布式框架，引起的service无法正常注入的问题
    @Bean(value = "userService")
    public UserService getUserService() {
        return userService;
    }



    @RequestMapping("queryUser")
    public List<UserModel> queryUser(){
        List<UserModel> list=userService.queryUser();

        return list;
    }

    @RequestMapping("addUser")
    @RequiresPermissions("user:openAdd")
    public void addUser(UserModel user){
        userService.addUser(user);
    }

    @RequestMapping("queryUserById")

    public UserModel queryUserById(Integer userId){

        return   userService.queryUserById(userId);
    }
    @RequestMapping("delAll")
    @RequiresPermissions("user:delAll")
    public  void delAll(String[] userId){
        userService.delAll(userId);

    }

    //导入
    @RequestMapping("importExcel")
    public String importExcel(MultipartFile file){
        try {
            InputStream inputStream =file.getInputStream();
            String filename = file.getOriginalFilename();
            boolean xls = ExcelUtils.isXls(filename);
            //创建excel工作薄
            Workbook wrkbook = null;
            if(xls){//03
                wrkbook = new HSSFWorkbook(inputStream);
            }else {//07
                wrkbook = new XSSFWorkbook(inputStream);
            }
            //获取sheet页
            Sheet sheet = wrkbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            List<UserModel> list=new ArrayList<>();
            for (int i=0;i<rowNum;i++){
                Row row = sheet.getRow(i+1);
                String userId = row.getCell(0).getStringCellValue();
                String username = row.getCell(1).getStringCellValue();
                String userpwd = row.getCell(2).getStringCellValue();
                String userbirthday = row.getCell(3).getStringCellValue();
                UserModel userModel=new UserModel();
                userModel.setUsername(username);
                userModel.setUserpwd(userpwd);
                userModel.setUserbirthday(userbirthday);
                list.add(userModel);
            }
            //批量新增
            userService.addUSers(list);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //导出
    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response, String filename) throws IOException, IllegalAccessException {
        List<UserModel> list=userService.queryUser();
        String[] titles = {"id","用户名","密码","生日"};
        ExcelImEx.exportExcel(response,filename, Collections.singletonList(list),titles,"用户信息",UserModel.class);
    }


    @RequestMapping("deriveTable")
    public void deriveTable(HttpServletResponse response,String[] val){
        List<UserModel> ulist = new ArrayList<UserModel>();
        try {
            ulist = userService.queryUser();

            String title = "用户";
            String[] rowname = new String[val.length+1];
            List<Object[]> dataList = new ArrayList<Object[]>();

            if(val!=null && val.length>0){
                for (UserModel users:ulist){
                    Object[] obj = new Object[rowname.length];
                    int valindex = 1;
                    rowname[0] = "序号";
                    obj[0]=users.getUserId();
                    for (int i = 0;i<val.length;i++){
                        if(val[i].equals("1")){
                            rowname[valindex]="编号";
                            obj[valindex]=users.getUserId();
                        }
                        if(val[i].equals("2")){
                            rowname[valindex]="账户";
                            obj[valindex]=users.getUsername();
                        }
                        if(val[i].equals("3")){
                            rowname[valindex]="密码";
                            obj[valindex]=users.getUserpwd();
                        }
                        if(val[i].equals("4")){
                            rowname[valindex]="生日";
                            obj[valindex]=users.getUserbirthday();
                        }

                        valindex++;
                    }
                    dataList.add(obj);
                }
            }else {
                rowname= new String[]{"序号","编号","姓名","用户名","性别","生日"};
                for (UserModel users:ulist){
                    Object[] obj = new Object[rowname.length];
                    obj[0] = users.getUserId();
                    obj[1] = users.getUsername();
                    obj[2] = users.getUserpwd();
                    obj[3] = users.getUserbirthday();
                    dataList.add(obj);
                }
            }
            ExportExcel exportExcel = new ExportExcel(title,rowname,dataList,response);
            exportExcel.export();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("queryTree")

    public List<TreeModel> queryTree(){
        Subject subject = SecurityUtils.getSubject();
        UserModel user = (UserModel) subject.getPrincipal();
        return userService.queryTree(user.getUserId());
    }

  /*  @RequestMapping("queryTree")

    public List<TreeModel> queryTree(){

        return userService.queryTree();
    }*/

    @RequestMapping("login")
    public String login(UserModel user ){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken upt = new UsernamePasswordToken(user.getUsername(), user.getUserpwd());
        try{
            subject.login(upt);
            return "登录成功";
        }catch (UnknownAccountException e){
            return "账号不存在";
        }catch (IncorrectCredentialsException e){
            return "密码错误";
        }catch (AuthenticationException e){
            return "用户认证失败";
        }
    }
}
