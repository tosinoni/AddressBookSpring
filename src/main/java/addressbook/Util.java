package addressbook;

import java.util.Collection;

public class Util {
    public static <E> void addAllIfNotNull(Collection<E> list, Collection<? extends E> c) {
        if (c != null) {
            list.addAll(c);
        }
    }

    public static <E> void addIfNotNull(Collection<E> list, E c) {
        if (c != null) {
            list.add(c);
        }
    }

    public static<E> boolean isCollectionEmpty(Collection<E> list) {
        return list == null || list.isEmpty();
    }
}
