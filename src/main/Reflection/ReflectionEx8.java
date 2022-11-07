package Reflection;

import java.lang.reflect.Field;

/**
 * @see <a href="https://www.oracle.com/technical-resources/articles/java/javareflection.html">
 *     https://www.oracle.com/technical-resources/articles/java/javareflection.html</a>
 */
public class ReflectionEx8 {
    public double d;

    public static void main(String[] args) {
        try {
            Class cls = Class.forName("research.Reflection.ReflectionEx8");
            Field fld = cls.getField("d");
            ReflectionEx8 f2obj = new ReflectionEx8();

            System.out.println("d = " + f2obj.d);
            fld.setDouble(f2obj, 12.34);

            System.out.println("d = " + f2obj.d);

        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
