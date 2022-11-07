import org.junit.jupiter.api.Test;

/**
 * @see <a href="https://dev.to/tlylt/compile-time-type-vs-run-time-type-oop-java-3-5fgn">
 *     https://dev.to/tlylt/compile-time-type-vs-run-time-type-oop-java-3-5fgn</a>
 */
class CompareWithCompileAndRunTimeTest {
    class Paper {
        private int height;
        private int width;

        Paper(int height, int width) {
            this.height = height;
            this.width = width;
        }

        @Override
        public boolean equals(Object obj) {
            System.out.println("A");
            return this == obj;
        }

        //Overload
        public boolean equals(Paper p) {
            System.out.println("B");
            return this == p;
        }
    }

    @Test
    void test() {
        Paper paper = new Paper(297, 140);
        Object object = paper;

        object.equals( object ); // A
        /**
         * compile-time? {@link Object#equals(Object)}
         * run-time? {@link Paper#equals(Object)}
         */

        object.equals( (Paper) object ); // A
        /**
         * compile-time? {@link Object#equals(Object)}
         * run-time? {@link Paper#equals(Object)}
         *              => Why? Paper is subclass of Object
         */

        object.equals( paper ); // A
        /**
         * compile-time? {@link Object#equals(Object)}
         * run-time? {@link Paper#equals(Object)}
         *              => Why? Paper is subclass of Object
         */

        paper.equals( object ); // A
        /**
         * compile-time? {@link Paper#equals(Object)}
         * run-time? {@link Paper#equals(Object)}
         */

        paper.equals( (Paper) object ); // B
        /**
         * compile-time? {@link Paper#equals(Paper)}
         * run-time? {@link Paper#equals(Paper)}
         */

        paper.equals( paper ); // B
        /**
         * compile-time? {@link Paper#equals(Paper)}
         * run-time? {@link Paper#equals(Paper)}
         */
    }
}