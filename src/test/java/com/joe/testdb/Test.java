package com.joe.testdb;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.SerializationUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
//        List<Integer> list1 = Lists.newArrayList(1, 2, 3);
//        List<Integer> list2 = Lists.newArrayList(4,5);
//        List<List<Integer>> productList = Lists.cartesianProduct(list1,list2);
//        System.out.println(productList);
//
//        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
//        List<List<Integer>> partitionList = Lists.partition(list, 2);
//        System.out.println(partitionList);
        String str1 = null;
        String str2 = "";
        String str3 = " ";
        String str4 = "a,bc";
        System.out.println(StringUtils.isEmpty(str2));
        System.out.println(Lists.newArrayList(StringUtils.split(str4,",")));



        String str = "abcde";
        IOUtils.write(str, new FileOutputStream("./b.tx"), StandardCharsets.UTF_8);
        Map<String, String> map = Maps.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        byte[] serialize = SerializationUtils.serialize(map);
        Object deserialize = SerializationUtils.deserialize(serialize);
        System.out.println(deserialize);

    }
}
