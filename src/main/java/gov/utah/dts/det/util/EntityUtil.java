package gov.utah.dts.det.util;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

public final class EntityUtil {

  private EntityUtil() {

  }

  public static <T extends AbstractBaseEntity<?>> boolean collectionContains(T item, Collection<T> collection) {
    for (AbstractBaseEntity<?> abe : collection) {
      if (abe.getPk() != null && abe.getPk().equals(item.getPk())) {
        return true;
      }
    }
    return false;
  }

  public static <T extends AbstractBaseEntity<?>> boolean removeItem(T item, Collection<T> collection) {
    for (Iterator<T> itr = collection.iterator(); itr.hasNext();) {
      T abe = itr.next();
      if (abe.getPk() != null && abe.getPk().equals(item.getPk())) {
        itr.remove();
        return true;
      }
    }
    return false;
  }

  /**
   * it forces initialization of proxy lazy lists
   * 
   * @param list
   */
  public static void initialize(List<?> list) {
    if (!Hibernate.isInitialized(list)) {
      Hibernate.initialize(list);
    }
  }
}