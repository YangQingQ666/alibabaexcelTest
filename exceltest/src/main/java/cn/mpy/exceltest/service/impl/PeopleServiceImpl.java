package cn.mpy.exceltest.service.impl;

import cn.mpy.exceltest.entity.People;
import cn.mpy.exceltest.mapper.PeopleMapper;
import cn.mpy.exceltest.service.PeopleService;
import cn.mpy.exceltest.utils.ExcelUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.function.Consumer;

/**
 * peopleService实现类
 * @author mpy
 */
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, People> implements PeopleService {
    @Override
    public void importPeopleExcel(MultipartFile file) {
        ExcelUtil.importExcel(file,People.class,process());
    }
    public Consumer<List<?>> process(){
        return peoples ->this.baseMapper.addBatch((List<People>) peoples);
    }
}
