package cn.mpy.exceltest.controller;

import cn.mpy.exceltest.entity.People;
import cn.mpy.exceltest.service.PeopleService;
import cn.mpy.exceltest.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 *  people控制类
 * @author mpy
 */
@RestController
public class PeopleController {

    @Autowired
    private PeopleService peopleService;
    @PutMapping("/insert")
    public Object insertPeople(@RequestBody People people){
         peopleService.save(people);
         return people;
    }

    @GetMapping("/findlist")
    public Object findPeoples(){
        List<People> list = this.peopleService.list();
        return list;
    }
    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        System.out.println(new Date());
        List<People> list = this.peopleService.list();
        // 这里注意 有同学反应下载的文件名不对。这个时候 请别使用swagger 他会影像
        ExcelUtil.downLoadExcel(response,People.class,list,"abcd");
        System.out.println(new Date());
    }

    @PostMapping("/importEmployeExcel")
    public String importEmployeExcel(@RequestBody MultipartFile file) {
        System.out.println(new Date());
        try {
            peopleService.importPeopleExcel(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "导入失败";
        }
        System.out.println(new Date());
        return "导入成功";
    }
}
