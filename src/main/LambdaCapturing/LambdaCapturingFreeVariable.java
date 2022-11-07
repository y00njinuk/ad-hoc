package LambdaCapturing;

/**
 * 람다식 내부에서 외부변수를 참조하고 있는 상황에서 외부변수의 값을 변경하면 어떻게 될까?
 */

public class LambdaCapturingFreeVariable {
    @FunctionalInterface
    interface Interface {
        void interMethod();
    }

    private int val = 0;

    public void classMethod(int i) {
        int val = 0;
        Interface lambda = () -> System.out.println(++this.val);
//        Interface lambda2 = ()-> System.out.println(++i);     // error.
//        Interface lambda3 = ()-> System.out.println(++val);   // error.
//        Why? Variable used in lambda expression should be final or effectively final

        Interface lambda4 = () -> {
//                System.out.println(++this.val);               // error. Why?
        };

        lambda.interMethod();
    }

    public static void main(String[] args) {
        LambdaCapturingFreeVariable obj = new LambdaCapturingFreeVariable();
        obj.classMethod(0);
    }
}
