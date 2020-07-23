package com.wen.db.datasource;

/**
 * 数据源id存放在ThreadLocal中<br/>
 * 不同线程访问数据库不会有冲突
 *
 * @author denis.huang
 * @since 2017年2月15日
 */
class DynamicDataSourceHolder {
  private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

  static void putDataSource(String name) {
    HOLDER.set(name);
  }

  static String getDataSource() {
    return HOLDER.get();
  }

  static void removeDataSource() {
    HOLDER.remove();
  }
}
