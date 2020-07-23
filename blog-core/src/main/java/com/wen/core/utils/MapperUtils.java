package com.wen.core.utils;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author denis.huang
 * @since 2017/10/31
 */
public class MapperUtils {
  private MapperUtils() {
  }

  private static volatile Mapper mapper;

  private static Mapper getMapper() {
    if (mapper == null) {
      synchronized (MapperUtils.class) {
        if (mapper == null) {
          mapper = new DozerBeanMapper(Collections.singletonList("dozer-bean-mappings.xml"));
          if (mapper == null) {
            throw new RuntimeException("Mapper没有初始化!");
          }
        }
      }
    }
    return mapper;
  }

  public static <T> T map(Object src, Class<T> dist) {
    if (src == null) {
      return null;
    }

    return getMapper().map(src, dist);
  }

  public static <T> List<T> map(List<?> list, Class<T> dist) {
    if (list == null) {
      return null;
    }

    return list.stream().map((src) -> getMapper().map(src, dist)).collect(Collectors.toList());
  }
}
