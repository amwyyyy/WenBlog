package com.wen.core.utils.excel.imp;

/**
 * 导入excel自定义校验接口
 *
 * @author denis.huang
 */
public interface ImportExcelChecker {
  /**
   * 自定义校验方法
   *
   * @param value
   * @return
   */
  String check(Object value);
}
