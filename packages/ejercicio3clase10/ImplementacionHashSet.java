
package ejercicio3clase10;

public class ImplementacionHashSet<E> {
    private ImplementacionHashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public ImplementacionHashSet() {
        map = new ImplementacionHashMap<>();
    }

    public boolean add(E element) {
        return map.put(element, PRESENT) == null;
    }

    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    public void removeAll(ImplementacionArrayList<E> c) {
        for (int i = 0; i < c.size(); i++) {
            remove(c.get(i));
        }
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Object[] toArray(Object[] a) {
        // This is a simplified implementation and does not behave exactly like the original.
        Object[] r = a;
        int i = 0;
        for (Object e : map.keySet()) {
            if (i < r.length) {
                r[i++] = e;
            } else {
                // This is a simplified implementation and does not handle resizing.
            }
        }
        return r;
    }

    public ImplementacionHashSet<E> keySet() {
        return map.keySet();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (Object e : map.keySet()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(e);
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }
}
