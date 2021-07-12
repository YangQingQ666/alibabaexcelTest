package cn.mpy.exceltest;

import cn.mpy.exceltest.entity.People;
import cn.mpy.exceltest.mapper.PeopleMapper;
import cn.mpy.exceltest.service.PeopleService;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ExceltestApplicationTests {
    @Autowired
    private PeopleService peopleMapper;

    @Test
    void contextLoads() {
        People people = peopleMapper.getOne(null);
        System.out.println(people.getPage());
    }
    @Test
    public void writeExcelOneSheetOnceWrite() throws IOException {
        System.out.println(new Date());
        File file = new File("E:\\temp\\");
        if (!file.exists()){
            file.mkdirs();
        }
        // 生成EXCEL并指定输出路径
        OutputStream out = new FileOutputStream("E:\\temp\\withoutHead1.xlsx");

        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);

        // 设置SHEET
        Sheet sheet = new Sheet(1, 0);
        sheet.setSheetName("sheet1");

        // 设置标题
        Table table = new Table(1);
        List<List<String>> titles = new ArrayList<List<String>>();
        titles.add(Arrays.asList("用户ID"));
        titles.add(Arrays.asList("名称"));
        titles.add(Arrays.asList("性别"));
        titles.add(Arrays.asList("年龄"));
        titles.add(Arrays.asList("邮箱"));
        table.setHead(titles);

        // 查询数据导出即可 比如说一次性总共查询出100条数据
        List<List<String>> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            userList.add(Arrays.asList("ID_" + i, "小明" + i, String.valueOf(i), new Date().toString()));
        }
        List<People> list = this.peopleMapper.list();
        list.forEach(people->{
            userList.add(Arrays.asList(String.valueOf(people.getId()),people.getPname(),people.getPsex(),String.valueOf(people.getPage()),people.getPemail()));
        });
        writer.write0(userList, sheet, table);
        writer.finish();
        System.out.println(new Date());
    }
    @Test
    void test1(){
        System.out.println(new Date());
    }
}
