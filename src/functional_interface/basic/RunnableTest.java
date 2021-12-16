package functional_interface.basic;

public class RunnableTest {
    public static void main(String[] args) {
        Runnable runnable = ()-> System.out.println("run alone ");

        System.out.println(" first print");
        runnable.run();
        System.out.println(" third print");
    }
}
