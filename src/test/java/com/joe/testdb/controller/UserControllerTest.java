package com.joe.testdb.controller;

import com.joe.testdb.controller.testuser.TestUserController;
import com.joe.testdb.module.user.mapper.TestUserMapper;
import com.joe.testdb.module.user.service.TestUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestUserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TestUserService testUserService;

    @MockBean
    private TestUserMapper testUserMapper;


    @Test
    public void create() throws Exception {
        String testRequestPath = "/user/create";

        this.mockMvc.perform(post(testRequestPath)
                        .contentType(MediaType.APPLICATION_JSON)// 设置ContentType
                        //.header()
                        .content("{}") // 设置Body
                        .accept(MediaType.TEXT_PLAIN)
                ).andExpect(status().isOk()) // 断言请求状态
        ;
    }
}
