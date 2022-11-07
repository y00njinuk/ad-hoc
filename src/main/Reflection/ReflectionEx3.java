package Reflection;

import java.lang.reflect.Method;

/**
 * @see <a href="https://www.oracle.com/technical-resources/articles/java/javareflection.html">
 *     https://www.oracle.com/technical-resources/articles/java/javareflection.html</a>
 */
public class ReflectionEx3 {
    private int f1(Object p, int x) throws NullPointerException {
        if (p == null)
            throw new NullPointerException();
        return x;
    }

    public static void main(String[] args) {
        try {
            Class cls = Class.forName("research.Reflection.ReflectionEx3");

            Method methlist[] = cls.getDeclaredMethods();
            for (Method m : methlist) {
                System.out.println("name = " + m.getName());
                System.out.println("decl class = " + m.getDeclaringClass());

                Class pvec[] = m.getParameterTypes();
                for (int j = 0; j < pvec.length; j++)
                    System.out.println("param #" + j + " " + pvec[j]);

                Class evec[] = m.getExceptionTypes();
                for (int j = 0; j < evec.length; j++)
                    System.out.println("exec #" + j + " " + evec[j]);

                System.out.println("return type = " + m.getReturnType());
                System.out.println("-----------------");
            }
        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
