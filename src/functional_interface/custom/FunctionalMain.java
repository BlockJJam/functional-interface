package functional_interface.custom;

public class FunctionalMain {
    public static void main(String[] args) {
        CustomInterface<String> customInterface = () -> "Hello Custom Abstract Method";

        String s = customInterface.funcCall();
        System.out.println( s);

        customInterface.printDefault();

        CustomInterface.printStatic();
    }
}
