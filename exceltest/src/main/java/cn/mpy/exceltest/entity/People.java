package cn.mpy.exceltest.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ContentRowHeight(40) // 设置 Cell 高度 为50
@HeadRowHeight(25)    //  设置表头 高度 为 40
@HeadFontStyle(fontName = "宋体",fontHeightInPoints = 14,bold = true) //表头字体样式
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER) //表头水平垂直
/**
 * 实体类
 * @author mpy
 */
public class People implements Serializable {
    @ColumnWidth(26) // 设置 Cell 宽度
    @ExcelProperty(value = { "编号" }, index = 0) // 设置 表头 信息
    @ExcelIgnore //不导出该字段
    private String id;
    @ColumnWidth(13) // 设置 Cell 宽度
    @ExcelProperty(value = { "姓名" }, index = 1) // 设置 表头 信息
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER) //内容水平垂直居中
    private String pname;
    @ColumnWidth(13) // 设置 Cell 宽度
    @ExcelProperty(value = { "年龄" }, index = 2) // 设置 表头 信息
    private Integer page;
    @ColumnWidth(13) // 设置 Cell 宽度
    @ExcelProperty(value = { "性别" }, index = 3) // 设置 表头 信息
    private String psex;
    @ColumnWidth(26) // 设置 Cell 宽度
    @ExcelProperty(value = { "邮箱" }, index = 4) // 设置 表头 信息
    private String pemail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getPsex() {
        return psex;
    }

    public void setPsex(String psex) {
        this.psex = psex;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }
}
