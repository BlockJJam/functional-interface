package functional_interface.basic;

import java.util.function.Predicate;

public class PredicateTest {
    public static void main(String[] args) {
        Predicate<Integer> isOverThanTen = num -> num > 10;
        Predicate<Integer> isLowerThanTen = num -> num < 10;

        // test
        System.out.println("20 is over than 10? " + isOverThanTen.test(20));
        System.out.println("20 is lower than 10? " + isLowerThanTen.test(20));

        // and
        System.out.println(isLowerThanTen.and(isOverThanTen).test(20));

        // or
        System.out.println(isLowerThanTen.or(isOverThanTen).test(20));

        // negative
        System.out.println("20 is not over than 10? "+isOverThanTen.negate().test(20));
        System.out.println("20 is not lower than 10? "+Predicate.not(isLowerThanTen).test(20));

        // isEqual
        System.out.println("20 is equal to 10? "+ Predicate.isEqual(10).test(20));

    }
}
