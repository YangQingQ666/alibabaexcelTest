package cn.mpy.exceltest.mapper;

import cn.mpy.exceltest.entity.People;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * PeopleMapper
 * @author mpy
 */
public interface PeopleMapper extends BaseMapper<People> {
    /**
     * people批量新增
     * @param peoples
     * @return
     */
    int addBatch(List<People> peoples);
}
