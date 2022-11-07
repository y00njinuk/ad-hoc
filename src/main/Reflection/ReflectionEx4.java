package Reflection;

import java.lang.reflect.Constructor;

/**
 * @see <a href="https://www.oracle.com/technical-resources/articles/java/javareflection.html">
 *     https://www.oracle.com/technical-resources/articles/java/javareflection.html</a>
 */
public class ReflectionEx4 {
    public ReflectionEx4() {}
    protected ReflectionEx4(int i, double d) {}

    public static void main(String[] args) {
        try {
            Class cls = Class.forName("research.Reflection.ReflectionEx4");
            Constructor ctorlist[] = cls.getDeclaredConstructors();

            for (Constructor ct : ctorlist) {
                System.out.println("name=" + ct.getName());
                System.out.println("decl class=" + ct.getDeclaringClass());

                Class pvec[] = ct.getParameterTypes();
                for (int j = 0; j < pvec.length; j++)
                    System.out.println("param #" + j + " " + pvec[j]);

                Class evec[] = ct.getExceptionTypes();
                for (int j = 0; j < evec.length; j++)
                    System.out.println("exc #" + j + " " + evec[j]);

                System.out.println("--------------------");
            }
        } catch (Throwable e) {
            System.out.println(e);
        }
    }
}
