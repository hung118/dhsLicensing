package gov.utah.dts.det.util;

import java.lang.reflect.Field;
import java.text.NumberFormat;

/**
 * 
 * @author jtorres
 * 
 *         General utils that we can use in the app. We'll use it to deal with null and general conversions
 */
public class CommonUtils {

  public static String fromDoubleToCurrency(Double amt) {
    String toRet = "";

    if (amt == null) {
      return toRet;
    } else {
      NumberFormat nf = NumberFormat.getCurrencyInstance();
      toRet = nf.format(amt);
    }
    return toRet;
  }

  /**
   * This util will help you find out if the object has any data in it It doesn't go through the hierarchy, it goes through the
   * data attributes of this instance. All errors are consumed, meaning if you pass a null, it will return false.
   * 
   * Again it only returns true or false
   * 
   * 
   * @param objectClazz
   * @return false if the object being is pass is null
   */
  public static boolean hasData(Object objectClazz) {
    if (objectClazz == null) {
      return false;
    }
    boolean toRet = false;
    Field[] fields = objectClazz.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        if (field.get(objectClazz) != null && field.get(objectClazz).toString().trim().length() > 0) {
          toRet = true;
          break;
        }
      } catch (Exception e) {
        // do nothing. Return false
        return false;
      }
    }

    return toRet;
  }

}
