package com.joe.testdb.taskUnused;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TaskInfoRepository extends BaseMapper<TaskInfo> {

}

