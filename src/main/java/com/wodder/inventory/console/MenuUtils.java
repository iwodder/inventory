package com.wodder.inventory.console;

import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

public class MenuUtils {

  private static final char KEY_VALUE_DELIMITER = '=';
  private static final char DOUBLE_QUOTE = '"';
  private static final char SINGLE_SPACE = ' ';

  private MenuUtils() {
  }

  public static Map<String, String> extractKeyValuePairs(String in) {
    PrimitiveIterator.OfInt chars = in.chars().iterator();
    Map<String, String> valuesMap = new HashMap<>();
    StringBuilder buff = new StringBuilder();
    while (chars.hasNext()) {
      char c = (char) chars.nextInt();
      if (c != KEY_VALUE_DELIMITER) {
        buff.append(c);
      } else {
        String key = getAndClearBuffer(buff);
        extractValue(chars, buff);
        String value = getAndClearBuffer(buff);
        valuesMap.put(key.toUpperCase(), value);
      }
    }
    return valuesMap;
  }

  private static void extractValue(PrimitiveIterator.OfInt chars, StringBuilder buff) {
    while (chars.hasNext()) {
      char c = (char) chars.nextInt();
      if (c != SINGLE_SPACE) {
        if (c == DOUBLE_QUOTE) {
          while (chars.hasNext()) {
            c = (char) chars.nextInt();
            if (c != DOUBLE_QUOTE) {
              buff.append(c);
            } else {
              break;
            }
          }
        } else {
          buff.append(c);
        }
      } else {
        break;
      }
    }
  }

  private static String getAndClearBuffer(StringBuilder buff) {
    String s = buff.toString();
    buff.delete(0, buff.length());
    return s;
  }
}
