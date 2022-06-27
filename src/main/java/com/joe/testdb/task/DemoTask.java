package com.joe.testdb.task;

import org.springframework.stereotype.Component;

@Component("demoTask")
public class DemoTask {
    public void taskWithParams(String params) {

        System.out.println("执行有参示例任务：" + params);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我是我啊");
    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
    }
}
