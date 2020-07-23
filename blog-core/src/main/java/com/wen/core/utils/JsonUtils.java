package com.wen.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.core.exception.ErrorCode;
import com.wen.core.web.ResultObject;

import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author denis.huang
 * @since 2017/10/10
 */
public class JsonUtils {
  public static String toJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  public static void writeToJson(ServletResponse response, ErrorCode errorCode)
      throws IOException {
    ResultObject resp = new ResultObject();
    resp.setErrCode(errorCode);

    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write(JsonUtils.toJson(resp));
  }

  private static final String NEW_LINE = "\r\n";

  /**
   * 格式化json
   */
  public static String format(String json) {
    try {
      // 去除原来的格式
      json = json.replace("\n", "").replace("\r", "").replace("\t", "");

      StringBuilder sb = new StringBuilder();
      // 上一状态
      int prevStatus = 0;
      // 缩进层级
      int level = 0;

      for (char c : json.toCharArray()) {
        int oper = getOperation(prevStatus, c);
        switch (oper) {
          case 1:
            sb.append(NEW_LINE).append(getTab(level));
            break;
          case 2:
            level++;
            sb.append(NEW_LINE).append(getTab(level));
            break;
          case 3:
            level--;
            sb.append(NEW_LINE).append(getTab(level));
            break;
          case 4:
            sb.append(' ');
            break;
          default:
        }
        sb.append(c);
        prevStatus = getStatus(c);
      }

      return sb.toString();
    } catch (Exception e) {
      return json;
    }
  }

  /**
   * 返回操作：0直接输出，1换行，2增加缩进并换行，3减少缩进并换行，4前面加空格
   **/
  private static int[][] operationArr = new int[][]{{0, 0, 0, 0, 0, 3}, // 普通字符
      {1, 2, 2, 0, 0, 0}, // {[
      {2, 0, 4, 0, 0, 0}, // :
      {3, 1, 4, 0, 0, 0}, // ,
      {4, 0, 0, 0, 0, 3},};// }]

  /**
   * 根据前一状态和当前字符决定操作
   **/
  private static int getOperation(int status, char c) {
    return operationArr[status][getStatus(c) + 1];
  }

  /**
   * 字符转换成状态
   **/
  private static int getStatus(char c) {
    int status = 0;
    switch (c) {
      case '{':
      case '[':
        status = 1;
        break;
      case ':':
        status = 2;
        break;
      case ',':
        status = 3;
        break;
      case '}':
      case ']':
        status = 4;
        break;
      default:
    }

    return status;
  }

  private static String getTab(int level) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < level; i++) {
      sb.append("    ");
    }
    return sb.toString();
  }
}
