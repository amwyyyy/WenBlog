package com.wen.db.datasource;

import com.wen.core.utils.RandomUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态选择数据源
 *
 * @author denis.huang
 * @since 2017年2月15日
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
  private final static String MASTER = "master";
  private final static String SLAVE = "slave";

  /**
   * 主库数据源
   */
  private DataSource masterDataSource;

  /**
   * 从库数据源
   */
  private List<DataSource> slaveDataSources;

  @Override
  protected Object determineCurrentLookupKey() {
    try {
      String dataSource = DynamicDataSourceHolder.getDataSource();

      // 默认情况取主库
      if (dataSource == null) {
        return MASTER;
      }

      // 随机选择从库
      if (SLAVE.equals(dataSource) && slaveDataSources.size() > 1) {
        dataSource = SLAVE + RandomUtils.next(0, slaveDataSources.size() - 1);
      }

      return dataSource;
    } finally {
      DynamicDataSourceHolder.removeDataSource();
    }
  }

  private void setTargetDataSources() {
    if (masterDataSource != null && slaveDataSources != null) {
      Map<Object, Object> targetDataSources = new HashMap<>(slaveDataSources.size() + 1);

      targetDataSources.put(MASTER, masterDataSource);

      // 给从库加上序号，方便选择
      for (int n = 0; n < slaveDataSources.size(); n++) {
        targetDataSources.put(SLAVE + n, slaveDataSources.get(n));
      }

      super.setTargetDataSources(targetDataSources);
    }
  }

  public void setMasterDataSource(DataSource masterDataSource) {
    this.masterDataSource = masterDataSource;
    setTargetDataSources();
  }

  public void setSlaveDataSources(List<DataSource> slaveDataSources) {
    this.slaveDataSources = slaveDataSources;
    setTargetDataSources();
  }
}
