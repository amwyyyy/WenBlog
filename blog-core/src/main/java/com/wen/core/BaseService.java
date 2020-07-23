package com.wen.core;

import com.github.pagehelper.Page;
import com.wen.core.exception.BusinessException;
import com.wen.core.exception.ErrorCode;
import com.wen.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * service基类
 *
 * @author denis.huang
 * @since 2017年2月15日
 */
public abstract class BaseService {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 分页对象类型转换工具
   *
   * @param list
   * @param trans
   * @return
   */
  protected static <T, E> Page<E> pageConvert(Page<T> list, Function<T, E> trans) {
    Page<E> page = new Page<>(list.getPageNum(), list.getPageSize());
    page.setTotal(list.getTotal());
    page.setPageSizeZero(list.getPageSizeZero());

    page.addAll(list.stream().map(trans).collect(Collectors.toList()));

    return page;
  }

  /**
   * 集合类型转换工具
   *
   * @param list
   * @param trans
   * @return
   */
  protected static <T, E> List<E> listConvert(List<T> list, Function<T, E> trans) {
    return list.stream().filter(Objects::nonNull).map(trans).collect(Collectors.toList());
  }

  /**
   * 抛出业务异常
   *
   * @param errorCode
   */
  protected void throwBizException(ErrorCode errorCode) {
    throw new BusinessException(errorCode);
  }

  /**
   * 抛出业务异常
   *
   * @param errorCode
   * @param errorMsg  指定错误消息
   */
  protected void throwBizException(ErrorCode errorCode, String errorMsg) {
    throw new BusinessException(errorCode, errorMsg);
  }

  /**
   * 非空检查
   *
   * @param t
   * @param <T>
   */
  protected <T> void checkNotNull(T t) {
    if (t == null) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * 字符串非空检查
   *
   * @param s
   */
  protected void checkNotEmpty(CharSequence s) {
    if (StringUtils.isEmpty(s)) {
      throw new IllegalArgumentException();
    }
  }
}
