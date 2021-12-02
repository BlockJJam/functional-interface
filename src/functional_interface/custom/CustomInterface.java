package functional_interface.custom;

@FunctionalInterface
public interface CustomInterface<T> {
    T funcCall();

    default void printDefault(){
        System.out.println("Hello Default!");
    }

    static void printStatic(){
        System.out.println("Hello Static");
    }

    // compile error: @FunctionalInterface에 의해 필터링된다
    //void printNormal();
}
