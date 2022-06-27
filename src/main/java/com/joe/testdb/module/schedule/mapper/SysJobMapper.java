package com.joe.testdb.module.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joe.testdb.module.schedule.entity.SysJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface SysJobMapper extends BaseMapper<SysJob> {
    List<SysJob> getSysJobListByStatus();
}
