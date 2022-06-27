package com.joe.testdb.util;

import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Slf4j
public class DtoConvertUtil {
    private DtoConvertUtil() {
    }
    public static <T, R> R copyFrom(T src, Supplier<R> supplier) {
        if (src == null) {
            return null;
        }
        R dst = supplier.get();
        BeanUtils.copyProperties(src, dst);
        return dst;
    }
    public static <T, R> List<R> copyFrom(List<T> srcList, Supplier<R> supplier) {
        if (CollectionUtils.isEmpty(srcList)) {
            return new ArrayList<>();
        }
        List<R> destList = srcList.stream().map(src -> copyFrom(src, supplier)).collect(Collectors.toList());
        return destList;
    }
    public static <T, R> R copyFrom(T src, R supplier) {
        if (src == null || supplier == null) {
            return null;
        }
        R dst = supplier;
        BeanUtils.copyProperties(src, dst);
        return dst;
    }
}

