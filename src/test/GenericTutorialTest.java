import org.junit.Test;

public class GenericTutorialTest {
    public class Box {
        private Object object;

        public void set(Object object) {
            this.object = object;
        }

        public Object get() {
            return object;
        }
    }

    public class GBox<T> {
        // T stands for "Type"
        private T t;

        public void set(T t) {
            this.t = t;
        }

        public T get() {
            return t;
        }
    }

    @Test
    public void test() {
        Box box = new Box();
        box.set(1);

        Integer a = (Integer) box.get();
        String b = (String) box.get();
        // compiler is ok.
        // but, ClassCastException in run time.

        GBox<Integer> gbox = new GBox<Integer>();
        gbox.set(1);

        Integer a1 = (Integer) gbox.get();
        // Inconvertible types; cannot cast 'java.lang.Integer' to 'java.lang.String'
        // String b2 = (String) gbox.get();
    }
}