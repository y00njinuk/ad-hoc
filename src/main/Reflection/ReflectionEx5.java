package Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @see <a href="https://www.oracle.com/technical-resources/articles/java/javareflection.html">
 *     https://www.oracle.com/technical-resources/articles/java/javareflection.html</a>
 */
public class ReflectionEx5 {
    private double d;
    public static final int i = 37;
    String s = "testing";

    public static void main(String[] args) {
        try {
            Class cls = Class.forName("research.Reflection.ReflectionEx5");

            Field fieldlist[] = cls.getDeclaredFields();

            for(Field fld: fieldlist) {
                System.out.println("name=" + fld.getName());
                System.out.println("decl class = " + fld.getDeclaringClass());
                System.out.println("type = " + fld.getType());

                int mod = fld.getModifiers();
                System.out.println("modifiers = " + Modifier.toString(mod));
                System.out.println("---------------");
            }

        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
