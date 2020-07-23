package com.wen.core.spring;

import com.wen.core.utils.DateUtils;
import com.wen.core.utils.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

/**
 * 时间类型数据转换器
 *
 * @author denis
 */
public class MultiDateFormatEditor extends PropertyEditorSupport {
  private static int LONG_DATE = 19;

  private static int SHORT_DATE = 14;

  @Override
  public String getAsText() {
    Date value = (Date) getValue();
    return value == null ? "" : DateUtils.DEFAULT_DATETIME_FORMATER.get().format(value);
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    if (StringUtils.isEmpty(text)) {
      setValue(null);
    } else {
      Date dt;
      try {
        if (text.length() == LONG_DATE) {
          dt = DateUtils.DEFAULT_DATETIME_FORMATER.get().parse(text);
        } else if (text.length() == SHORT_DATE) {
          dt = DateUtils.YYYYMMDDHHMMSS.get().parse(text);
        } else {
          dt = DateUtils.DEFAULT_DATE_FORMATER.get().parse(text);
        }
      } catch (ParseException ex) {
        throw new IllegalArgumentException(text);
      }
      setValue(dt);
    }
  }
}
