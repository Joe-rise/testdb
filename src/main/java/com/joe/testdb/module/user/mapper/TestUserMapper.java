package com.joe.testdb.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joe.testdb.module.user.entity.TestUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestUserMapper extends BaseMapper<TestUser> {
}
