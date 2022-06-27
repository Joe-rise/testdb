package com.joe.testdb.module.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.testdb.constants.BootError;
import com.joe.testdb.exception.BusinessException;
import com.joe.testdb.module.user.entity.TestUser;
import com.joe.testdb.module.user.mapper.TestUserMapper;
import com.joe.testdb.util.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestUserService extends ServiceImpl<TestUserMapper, TestUser> {

    @Transactional(rollbackFor = Exception.class)
    public String create(TestUser testUser) throws BusinessException {
        Integer res;
        try{
            res = baseMapper.insert(testUser);
        }catch (DuplicateKeyException e){
            throw new BusinessException(BootError.DATA_EXIST);
        }
        if (res <= 0 || StringUtils.isEmpty(testUser.getId())) {
            throw new BusinessException(BootError.DB_FAILED, "test_user", "create", JacksonUtils.toJsonStrByJackson(testUser));
        }
        return testUser.getId();
    }
}
