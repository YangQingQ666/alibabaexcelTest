package cn.mpy.exceltest.service;

import cn.mpy.exceltest.entity.People;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * PeopleServcie
 * @author mpy
 */
public interface PeopleService extends IService<People> {
     /**
      * 文件上传
      * @param file
      */
     void importPeopleExcel(MultipartFile file);
}
