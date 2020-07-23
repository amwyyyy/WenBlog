package com.wen.core.utils.excel.imp;

/**
 * 导入excel错误消息体
 *
 * @author denis.huang
 */
public class ImportError {
  /**
   * 行
   */
  private Integer row;
  /**
   * 列
   */
  private Integer col;
  /**
   * 错误消息
   */
  private String msg;

  public static ImportError makeError(int row, int col, String msg) {
    ImportError error = new ImportError();
    error.setCol(col);
    error.setRow(row);
    error.setMsg(msg);
    return error;
  }

  public Integer getRow() {
    return row;
  }

  public void setRow(Integer row) {
    this.row = row;
  }

  public Integer getCol() {
    return col;
  }

  public void setCol(Integer col) {
    this.col = col;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
