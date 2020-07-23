package com.wen.core.utils;

/**
 * 来自于twitter项目<a href= "https://github.com/twitter/snowflake">snowflake</a>的id产生方案，全局唯一，时间有序
 *
 * @author snowflake
 */
public class IdWorker {
  private final long workerId;
  private final static long TWEPOCH = 1303895660503L;
  private long sequence = 0L;
  /**
   * 机器标识位数
   **/
  private final static long WORKER_ID_BITS = 10L;
  /**
   * 机器ID最大值
   **/
  private final static long MAX_WORKER_ID = -1L ^ -1L << WORKER_ID_BITS;
  private final static long SEQUENCE_BITS = 12L;

  private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
  private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
  private final static long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;

  private long lastTimestamp = -1L;

  public IdWorker(final long workerId) {
    super();
    if (workerId > MAX_WORKER_ID || workerId < 0) {
      throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", IdWorker.MAX_WORKER_ID));
    }
    this.workerId = workerId;
  }

  public synchronized long nextId() {
    long timestamp = this.timeGen();
    if (this.lastTimestamp == timestamp) {
      this.sequence = this.sequence + 1 & IdWorker.SEQUENCE_MASK;
      if (this.sequence == 0) {
        timestamp = this.tilNextMillis(this.lastTimestamp);
      }
    } else {
      this.sequence = 0;
    }
    if (timestamp < this.lastTimestamp) {
      throw new RuntimeException(
          String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
    }

    this.lastTimestamp = timestamp;
    return timestamp - TWEPOCH << IdWorker.TIMESTAMP_LEFT_SHIFT | this.workerId << IdWorker.WORKER_ID_SHIFT | this.sequence;
  }

  private long tilNextMillis(final long lastTimestamp) {
    long timestamp = this.timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = this.timeGen();
    }
    return timestamp;
  }

  private long timeGen() {
    return System.nanoTime() / 1000000;
  }
}
