package Reflection;

import java.lang.reflect.Constructor;

/**
 * @see <a href="https://www.oracle.com/technical-resources/articles/java/javareflection.html">
 *     https://www.oracle.com/technical-resources/articles/java/javareflection.html</a>
 */
public class ReflectionEx7 {
    public ReflectionEx7() { }
    public ReflectionEx7(int a, int b) {
        System.out.println("a = " + a + " b = " + b);
    }

    public static void main(String[] args) {
        try {
            Class cls = Class.forName("research.Reflection.ReflectionEx7");
            Class partypes[] = new Class[2];
            partypes[0] = Integer.TYPE;
            partypes[1] = Integer.TYPE;

            Constructor ct = cls.getConstructor(partypes);

            Object arglist[] = new Object[2];
            arglist[0] = new Integer(37);
            arglist[1] = new Integer(47);

            Object retobj = ct.newInstance(arglist);

        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
