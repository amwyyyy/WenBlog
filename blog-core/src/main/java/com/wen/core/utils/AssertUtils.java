package com.wen.core.utils;

import com.wen.core.exception.BusinessException;
import com.wen.core.exception.ErrorCodeConst;

/**
 * 断言工具类
 */
public class AssertUtils {
  private AssertUtils() {
  }

  public static void notNull(Object obj) {
    if (obj == null) {
      throw new BusinessException(ErrorCodeConst.ERRCODE_PARAM, "not null");
    }
  }

  public static void notEmpty(String obj) {
    if (StringUtils.isEmpty(obj)) {
      throw new BusinessException(ErrorCodeConst.ERRCODE_PARAM, "not empty");
    }
  }
}
