## 람다식으로 많이 사용하는 기본적인 Functional Interface 종류

### **함수형 인터페이스** - **Descripter** - **Method**

- Predicate - `T -> boolean` - `boolean test(T t)`

- Consumer - `T -> void` - `void accept(T t)`

- Supplier - `() -> T` - `T get()`

- Function<T,R> - `T -> R` - `R apply(T t)`

- Comparator - `(T, T) -> int` - `int compare(T o1, T o2)`

- Runnable - `() -> void` - `void run()`

- Callable - `() -> T` - `V call()`


- 이외에도 많이 있다, 어느 정도 있나 확인만 해볼까?
  ![Untitled](https://user-images.githubusercontent.com/57485510/145821229-aad32ae3-39da-4292-9475-381e9df97a23.png)

---

## Predicate

- 역할
    - argument 하나(= T)를 받아서 `boolean` 타입을 리턴
    - `T -> boolean` 로 표현
    - 인터페이스 코드

    ```java
    public interface Predicate<T> {
        boolean test(T var1);
    
        default Predicate<T> and(Predicate<? super T> other) {
            Objects.requireNonNull(other);
            return (t) -> {
                return this.test(t) && other.test(t);
            };
        }
    
        default Predicate<T> negate() {
            return (t) -> {
                return !this.test(t);
            };
        }
    
        default Predicate<T> or(Predicate<? super T> other) {
            Objects.requireNonNull(other);
            return (t) -> {
                return this.test(t) || other.test(t);
            };
        }
    
        static <T> Predicate<T> isEqual(Object targetRef) {
            return null == targetRef ? Objects::isNull: (object) -> {
                return targetRef.equals(object);
            };
        }
    
        static <T> Predicate<T> not(Predicate<? super T> target) {
            Objects.requireNonNull(target);
            return target.negate();
        }
    }
    ```

- 사용법
    - Functional Interface의 공통적인 특징은, 람다식을 통해 함수의 형태를 두고, default 메서드를 활용가능하다는 점입니다
    - `Predicate` 에제 코드

    ```java
    package functional_interface.basic;
    
    import java.util.function.Predicate;
    
    public class PredicateTest {
        public static void main(String[] args) {
            Predicate<Integer> isOverThanTen = num -> num > 10;
            Predicate<Integer> isLowerThanTen = num -> num < 10;
    
            // test 1)
            System.out.println("20 is over than 10? " + isOverThanTen.test(20));
            System.out.println("20 is lower than 10? " + isLowerThanTen.test(20));
    
            // and 2)
            System.out.println(isLowerThanTen.and(isOverThanTen).test(20));
    
            // or 3) 
            System.out.println(isLowerThanTen.or(isOverThanTen).test(20));
    
            // negative 4)
            System.out.println("20 is not over than 10? "+isOverThanTen.negate().test(20));
            System.out.println("20 is not lower than 10? "+Predicate.not(isLowerThanTen).test(20));
    
            // isEqual 5)
            System.out.println("20 is equal to 10? "+ Predicate.isEqual(10).test(20));
    
        }
    }
    ```

- Predicate 함수의 형태를 정의한 코드
    - `Predicate<Integer> isOverThanTen = num -> num > 10;`
    - `Predicate<Integer> isLowerThanTen = num -> num < 10;`
- Predicate 인터페이스의 default 코드에 대한 설명

    1) `test( arg1:Object )` | return: `Boolean`
       → arg1을 isOverThanTen 람다식에 넣어서 boolean 결과를 return 하는 코드

    2) `and( arg1:Predicate )` | return: `Predicate<T>`  
       → arg1 `Predicate`와 함께 `test(arg2)`의 arg2 매개변수를 검사하여 result1 && result2 연산 결과를 return 한다,

    3) `or( arg1:Predicate )` | return: `Predicate<T>`
       → arg1 `Predicate`와 함께 `test(arg2)`의 arg2 매개변수를 검사하여  result1 || result2 연산 결과를 return 한다.

    4) `negative()` | return: `Predicate<T>`
       → 해당 `Predicate`의 `test()`의 반대 결과를 return하는 람다식을 return 한다

    5) `isEqual( arg1:Object )` | return: `Predicate<T>`
       → Predicate의 static(정적) 메서드로 Predicate에서 기본적으로 제공하는 기능으로, arg1과 equal 비교를 할 수 있는 람다식을 제공한다


- 예제 결과물

```
20 is over than 10? true -- 1)
20 is lower than 10? false
false -- 2)
true
20 is not over than 10? false -- 3)
20 is not lower than 10? true -- 4)
20 is equal to 10? false -- 5)

Process finished with exit code 0
```

---

## Consumer

- 역할
  : 이름 그대로 인자를 1개 받아도 아무것도 리턴하지 않고 소비만하는 인터페이스입니다
  : `T -> void` 로 표현

    ```java
    @FunctionalInterface
    public interface Consumer<T> {
        void accept(T var1);
    
        default Consumer<T> andThen(Consumer<? super T> after) {
            Objects.requireNonNull(after);
            return (t) -> {
                this.accept(t);
                after.accept(t);
            };
        }
    }
    ```

- 사용법
    - 인자를 받아서 처리에 대한 결과를 return 받지 않고, 처리만 떠넘기는 곳에 사용
    - 메서드의 의미를 해석하자면,
      → accept 메서드 = 인자로 넘어온 매개변수에 람다식을 처리해줘
      → andThen 메서드 = 인자로 넘어온 람다식에도 accept 메서드 인자를 적용해줘를 반복 가능
    - `Consumer<T>`예제 코드

        ```java
        package functional_interface.basic;
        
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.function.Consumer;
        
        public class ConsumerTest {
            public static void main(String[] args) {
                Consumer<String> printString = text -> System.out.println("hello "+ text + "?");
                printString.accept("Block"); // accept 1)
        
                Consumer<String> printString2 = text -> System.out.println("Wow "+ text);
                printString.andThen(printString2).accept("Block"); // andthen + accept 2)
        
                Members member1 = new Members(0,"jjm","01022223333", 1, Arrays.asList("potion","sword"));
        
        				// 3) 로그라이크 게임이라고 생각해볼까요?
                Consumer<Members> dieToStart = members -> {
                  members.setLevel(0);
                  members.clearItems();
                };
        
                System.out.println("member1 spec: " +member1);
                dieToStart.accept(member1);
                System.out.println("member1 after die spec: " + member1);
        
            }
        
        // members 재료입니다.
            private static class Members{
                private int id;
                private String name;
                private String phone;
                private int level;
                private List<String> items = new ArrayList<>();
        
                public Members(int id, String name, String phone, int level, List<String> items) {
                    this.id = id;
                    this.name = name;
                    this.phone = phone;
                    this.level = level;
                    this.items = items;
                }
        		
        				// ... getter ... setter
        
                public void clearItems(){
                    this.items = new ArrayList<>();
                }
        
                @Override
                public String toString() {
                    return "Members{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", phone='" + phone + '\'' +
                            ", level=" + level +
                            ", items=" + items +
                            '}';
                }
            }
        }
        ```

    - 1) `accept( arg1: T)` | return: `void`
         → 람다식의 `arg1`을 파라미터로 삽입해주는 메서드
    - 2) `andThen( arg1: Consumer<? super Consumer>` | return: `Consumer<T>`
         → accept 매개변수로 들어온 파라미터를 적용시킬 추가 `Consumer` 람다식을 `arg1`으로 삽입해준다
    - 3) 그냥 소비만 하면 되는 경우 하나를 예시로 넣었다
         → 로그라이크 게임에서, member가 죽으면 레벨(level)과 아이템 목록(items)을 날려버리는 케이스로 보자
- 예제 결과물

    ```
    hello Block? -- 1)
    hello Block? -- 2)
    Wow Block
    member1 spec: Members{id=0, name='jjm', phone='01022223333', level=1, items=[potion, sword]} -- 3)
    member1 after die spec: Members{id=0, name='jjm', phone='01022223333', level=0, items=[]}
    
    Process finished with exit code 0
    ```


---

## Supplier

- 역할
    - 아무런 인자를 받지 않고, T타입의 객체를 리턴합니다
    - `() -> T`  표현
    - 공급자라는 이름에 맞게 **받는 것 없이, 특정 객체 리턴**
    - 인터페이스 코드

    ```java
    package java.util.function;
    
    @FunctionalInterface
    public interface Supplier<T> {
        T get();
    }
    ```

- 사용법
    - Supplier는 get() 메서드를 통해, 인자 없이 T타입 객체를 리턴한다
    - `Supplier` 예제

    ```java
    package functional_interface.basic;
    
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Random;
    import java.util.function.Supplier;
    
    public class SupplierTest {
        public static void main(String[] args) {
            Supplier<Integer> integerRandomSupplier = () -> {
                int randNum = new Random().nextInt();
                return randNum;
            };
    
            int number = integerRandomSupplier.get(); // 1)
            System.out.println("random number using supplier: "+ Integer.toString(number));
    
            Supplier<Members> superMembersSupplier = () -> new Members(0,"super","01022223333",0, Arrays.asList("1","2"));
            Members superMember = superMembersSupplier.get(); //2)
            System.out.println("superMember = " + superMember);
    
        }
    
        private static class Members{
            private int id;
            private String name;
            private String phone;
            private int level;
            private List<String> items = new ArrayList<>();
    
            public Members(int id, String name, String phone, int level, List<String> items){
                this.id = id;
                this.name = name;
                this.phone = phone;
                this.level = level;
                this.items = items;
            }
    
        }
    }
    
    ```

    - `get()`  |  return: `T`
      → 1) `integerRandomSupplier`라는 `Supplier`인터페이스를 통해 랜덤한 정수를 `get()`메서드를 통해 가져온다
      → 2) `superMembersSupplier`를 통해 super멤버를 생성하여 `get()`메서드를 통해 할당받는다
- 예제 결과물

    ```
    random number using supplier: 1449326594
    superMember = Members{id=0, name='super', phone='01022223333', level=0, items=[1, 2]}
    
    Process finished with exit code 0
    ```


---

## Function<T,R>

- 역할
    - arg: `T` | return: `R` → `T -> R`
    - 수학식에서 함수처럼 특정 값을 받아서 다른 값으로 반환하는 것과 같다
    - T type = R type 일수도, T type ≠ R type 일 수도 있다
    - 인터페이스 코드

    ```java
    
    package java.util.function;
    
    import java.util.Objects;
    
    @FunctionalInterface
    public interface Function<T, R> {
        R apply(T var1);
    
        default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
            Objects.requireNonNull(before);
            return (v) -> {
                return this.apply(before.apply(v));
            };
        }
    
        default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (t) -> {
                return after.apply(this.apply(t));
            };
        }
    
        static <T> Function<T, T> identity() {
            return (t) -> {
                return t;
            };
        }
    }
    ```

- 사용법
    - T타입의 인자를 R타입의 객체를 리턴
    - apply메서드
- 예제 결과

    ```java
    package functional_interface.basic;
    
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.function.Function;
    
    public class FunctionTest {
        public static void main(String[] args) {
            Function<Integer, Double> divide = (value) -> Double.valueOf(value / 3.0f);
            
            Double result = divide.apply(5);  // 1)
            System.out.println("result = " + result);
    
            Function<Members, Double> multiplyLevel = (members) -> Double.valueOf(members.getLevel() * 1.5f);
            Function<Double, String> convertDoubleToString = (num)-> String.valueOf(num);
            Members newMember = new Members(0,"blockjjam","01013134545", 2, Arrays.asList("1","2"));
    
            // second.compose( Function first) <- Function first가 먼저 호출 // 2)
            String composedLevel = convertDoubleToString.compose(multiplyLevel).apply(newMember);
            System.out.println("composedLevel = " + composedLevel);
    
            // first.andThen( Function second) <- Function second가 먼저 호출  // 3)
            String andThenLevel = multiplyLevel.andThen(convertDoubleToString).apply(newMember);
            System.out.println("andThenLevel = " + andThenLevel);
    
            // 입력받는 객체 그대로를 return 해주는 정체성 확인 메서드? 같은 느낌, 용도는 잘 모르겠다;;  // 4)
            System.out.println(Function.identity().apply(newMember));
        }
    
        private static class Members{
            private int id;
            private String name;
            private String phone;
            private int level;
            private List<String> items = new ArrayList<>();
    
            public Members(int id, String name, String phone, int level, List<String> items) {
                this.id = id;
                this.name = name;
                this.phone = phone;
                this.level = level;
                this.items = items;
            }
    
    	       // getter, setter, toString()
        }
    }
    ```

    - 1) `apply( arg1: Integer )` | return: `Double`
         → arg1의 타입과 return 타입이 서로 다를 수 있다( 물론, 같은 타입도 가능)
    - 2) `compose( arg1: Function )` | return: `Function<T,R>`
         → `apply` 메서드와 함께 사용할 시에, `compose` 메서드의 `arg1`이 호출한 메서드보다 먼저 인자를 처리한다
    - 3) `andThen( arg1: Function )` | return: `Function<T,R>`
         → `compose` 메서드와는 반대로, `arg1` Function이 호출한 메서드보다 늦게 인자를 처리한다
    - 4) `identity()` | return: `Function<T,T>`
         → `apply( arg1 )`의 `arg1` 그대로를 반환해주는 메서드

---

## Comparator

- 역할
    - `T`타입 2개를 입력받아, `int` 타입을 return
    - `(T,T) -> int`
    - `Object` 정렬에 사용될 값을 지정하는 역할을 하는 인터페이스로 많이 사용
    - `compare()`를 추상메서드로 갖고 있지만, 많은 `static`, `default` 메서드로 구성되어 어떤 기능들이 하는지 알아보자
    - 인터페이스 코드

    ```java
    //
    // Source code recreated from a .class file by IntelliJ IDEA
    // (powered by FernFlower decompiler)
    //
    
    package java.util;
    
    import java.io.Serializable;
    import java.util.Comparators.NaturalOrderComparator;
    import java.util.Comparators.NullComparator;
    import java.util.function.Function;
    import java.util.function.ToDoubleFunction;
    import java.util.function.ToIntFunction;
    import java.util.function.ToLongFunction;
    
    @FunctionalInterface
    public interface Comparator<T> {
        int compare(T var1, T var2);
    
        boolean equals(Object var1);
    
        default Comparator<T> reversed() {
            return Collections.reverseOrder(this);
        }
    
        default Comparator<T> thenComparing(Comparator<? super T> other) {
            Objects.requireNonNull(other);
            return (Comparator)((Serializable)((c1, c2) -> {
                int res = this.compare(c1, c2);
                return res != 0 ? res : other.compare(c1, c2);
            }));
        }
    
        default <U> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
            return this.thenComparing(comparing(keyExtractor, keyComparator));
        }
    
        default <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor) {
            return this.thenComparing(comparing(keyExtractor));
        }
    
        default Comparator<T> thenComparingInt(ToIntFunction<? super T> keyExtractor) {
            return this.thenComparing(comparingInt(keyExtractor));
        }
    
        default Comparator<T> thenComparingLong(ToLongFunction<? super T> keyExtractor) {
            return this.thenComparing(comparingLong(keyExtractor));
        }
    
        default Comparator<T> thenComparingDouble(ToDoubleFunction<? super T> keyExtractor) {
            return this.thenComparing(comparingDouble(keyExtractor));
        }
    
        static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
            return Collections.reverseOrder();
        }
    
        static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
            return NaturalOrderComparator.INSTANCE;
        }
    
        static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) {
            return new NullComparator(true, comparator);
        }
    
        static <T> Comparator<T> nullsLast(Comparator<? super T> comparator) {
            return new NullComparator(false, comparator);
        }
    
        static <T, U> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
            Objects.requireNonNull(keyExtractor);
            Objects.requireNonNull(keyComparator);
            return (Comparator)((Serializable)((c1, c2) -> {
                return keyComparator.compare(keyExtractor.apply(c1), keyExtractor.apply(c2));
            }));
        }
    
        static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor) {
            Objects.requireNonNull(keyExtractor);
            return (Comparator)((Serializable)((c1, c2) -> {
                return ((Comparable)keyExtractor.apply(c1)).compareTo(keyExtractor.apply(c2));
            }));
        }
    
        static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) {
            Objects.requireNonNull(keyExtractor);
            return (Comparator)((Serializable)((c1, c2) -> {
                return Integer.compare(keyExtractor.applyAsInt(c1), keyExtractor.applyAsInt(c2));
            }));
        }
    
        static <T> Comparator<T> comparingLong(ToLongFunction<? super T> keyExtractor) {
            Objects.requireNonNull(keyExtractor);
            return (Comparator)((Serializable)((c1, c2) -> {
                return Long.compare(keyExtractor.applyAsLong(c1), keyExtractor.applyAsLong(c2));
            }));
        }
    
        static <T> Comparator<T> comparingDouble(ToDoubleFunction<? super T> keyExtractor) {
            Objects.requireNonNull(keyExtractor);
            return (Comparator)((Serializable)((c1, c2) -> {
                return Double.compare(keyExtractor.applyAsDouble(c1), keyExtractor.applyAsDouble(c2));
            }));
        }
    }
    ```

- 사용법
    - java의 generic → 재귀적 타입 바운드에 대한 이야기 잠깐만 확인하고 가자
        - **왜?** 그 함수 시그니처가 Comparator에 자주 등장하기 때문
        - **무엇인데? "**매개변수가 (자신을 포함하는) 수식에 의해 한정될 수 있다" 정도의 의미만 생각하고, 좀 더 확인하고 싶다면, 해당 링크를 참조하겠다 → [https://medium.com/@joongwon/java-java의-generics-604b562530b3#:~:text=선언할 수 있다.-,재귀적,-타입 바운드](https://medium.com/@joongwon/java-java%EC%9D%98-generics-604b562530b3#:~:text=%EC%84%A0%EC%96%B8%ED%95%A0%20%EC%88%98%20%EC%9E%88%EB%8B%A4.-,%EC%9E%AC%EA%B7%80%EC%A0%81,-%ED%83%80%EC%9E%85%20%EB%B0%94%EC%9A%B4%EB%93%9C)
        - 예제 코드

            ```java
            package functional_interface.basic;
            
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Comparator;
            import java.util.List;
            import java.util.function.Function;
            import java.util.stream.Collectors;
            
            public class ComparatorTest {
                public static void main(String[] args) {
                    // compare
                    Comparator<Integer> intComparator = (int1, int2) -> int1 - int2;
                    int intCompareResult = intComparator.compare(1,10);
                    System.out.println("intCompareResult = " + intCompareResult);
                    // equals
                    Comparator<Integer> otherComparator = (int1, int2) -> int2 - int1;
                    Comparator<Integer> intComparator2 = (int1, int2) -> int1 - int2;
                    boolean equalsIntComparator =intComparator.equals(otherComparator);
                    boolean equalsIntComparator2 =intComparator.equals(intComparator2);
            
                    System.out.println("equalsIntComparator = " + equalsIntComparator);
                    System.out.println("equalsIntComparator2 = " + equalsIntComparator2);
                    System.out.println("--------------------------------------------------");
            
                    List<Members> members = new ArrayList<>() {{
                        add(new Members(0, "jjm1", "010-1234", 0, null));
                        add(new Members(1,"jjm2","010-1234",2, null));
                        add(new Members(2,"jjm3","010-1234",1, null));
                        add(new Members(3,"jjm4","010-4321",3, null));
                        add(new Members(4,"jjm5","010-4321",2, null));
                        add(new Members(5,"jjm6","010-4321",0, null));
                    }};
                    
                    for(Members member : members)
                        System.out.println("member = " + member);
                    System.out.println("--------------------------------------------------");
            
                    // lambda + stream
                    Comparator<Integer> levelComparator = (lvFirst, lvSecond) -> lvFirst - lvSecond;
                    List<Integer> sortedLevels = members.stream()
                            .map(member -> member.getLevel())
                            .sorted(levelComparator)
                            .collect(Collectors.toList());
            
                    for( Integer sortedLevel : sortedLevels)
                        System.out.println("sortedLevel = " + sortedLevel);
                    System.out.println("--------------------------------------------------");
            
                    // comparing
                    Comparator<Members> byName = Comparator.comparing(Members::getName);
                    members.sort(byName);
            
                    for(Members m : members)
                        System.out.println("member byLevel = " + m);
                    System.out.println("--------------------------------------------------");
            
                    // comparingInt
                    Comparator<Members> byLevel = Comparator.comparingInt(Members::getLevel);
                    members.sort(byLevel);
                    System.out.println("--------------------------------------------------");
            
                    // naturalOrder, reversedOrder
                    members.stream()
                            .sorted(Comparator.naturalOrder())
                            .forEach(natural-> System.out.println("natural = " + natural));
                    System.out.println("--------------------------------------------------");
            
                    members.stream()
                            .sorted(Comparator.reverseOrder())
                            .forEach(reversed -> System.out.println("reversed = " + reversed));
                    System.out.println("--------------------------------------------------");
            
                    // nullLast, nullFirst
                    members.add(null);
                    members.add(new Members(6,"jjm7","010-1234",3, null));
                    members.stream()
                            .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
                            .forEach(nullLast -> System.out.println("nullLast = " + nullLast));
            
                    System.out.println("--------------------------------------------------");
                    members.stream()
                            .sorted(Comparator.nullsFirst(Comparator.naturalOrder()))
                            .forEach(nullFirst -> System.out.println("nullFirst = " + nullFirst));
                    System.out.println("--------------------------------------------------");
            
                    // thenComparing + comparing
                    Function<Members,String> phoneSort = m -> m.getPhone();
                    Function<Members, Integer> levelSort = m -> m.getLevel();
                    
                    members.stream()
                            .filter(m -> m != null)
                            .sorted(Comparator.comparing(phoneSort).thenComparing(levelSort)) // 1차: phone, 2차: name
                            .forEach(thenComparedMember -> System.out.println("thenComparedMember = " + thenComparedMember));
            
                }
            
                private static class Members implements Comparable<Members>{
                    private int id;
                    private String name;
                    private String phone;
                    private Integer level;
                    private List<String> items = new ArrayList<>();
            
                    public Members() {
                    }
            
                    public Members(int id, String name, String phone, int level, List<String> items) {
                        this.id = id;
                        this.name = name;
                        this.phone = phone;
                        this.level = level;
                        this.items = items;
                    }
            
                    // getter, setter, toString, compareTo, clone
                }
            }
            ```

        - 각 메서드 설명


| type bound /return  |  방법 및 설명 |
            | --- | --- |
            | int | compare( T o1, T o2) : 순서에 대한 2가지 인수를 비교한 결과를 int 타입으로 return |
            | <T,U extends Comparable<? super U>> / Comparator<T> | comparing(Function<? super T,? extends U> keyExtractor): 비교자에 필요한 객체를 뽑아내는 Function 객체를 통해 자연순서대로 비교하는 Comparator 객체를 리턴 |
            | <T,U> Comparator<T> | comparing( Function<? super T, ? extends U> keyExtractor,Comparator<? super U> keyComparator ) : |
            | <T> / Comparator<T> | comparingDouble(ToDoubleFunction<? super T> keyExtractor), comparingInt(ToIntFunction<? super T> keyExtractor), comparingLong(ToLongFunction<? super T> keyExtractor):  |
            | boolean | equals( Object obj ) : 비교 객체(Comparator 타입)가 서로 같은 객체인지 비교하여 boolean 타입을 리턴 |
            | <T extends Comparable<? super T>> / Comparator<T> | naturalOrder(), reversedOrder() : 자연율 순서로 비교하는 Comparator 객체를 리턴 |
            | <T> Comparator<T> | nullsFirst(Comparator<? super T> comparator), nullsLast(Comparator<? super T> comparator) : 객체 값이 null인 경우 첫번째(Last는 마지막)로 보내버리는 Comparator 객체를 리턴 |
            | Comparator<T> | reversed() : 현재 순서의 반대 순서로 만들어버리는 Comparator 객체를 리턴 |
            | <U extends Comparable<? super U>> / Comparator<T> | thenComparing(Comparator<? super T> other) : 다음 비교를 위한 Comparator 객체를 입력받아 (<First>.thenComparing(<Second>)라고 했을 때) First Comparator 객체로 먼저 비교한 뒤, Second Comparator를 그다음 순서로 비교해버리는 Comparator 객체를 리턴 |
            | <**U**> / Comparator<U> | thenComparing( Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) :   |
            | Comparator<T> | thenComparingDouble(ToDoubleFunction<? super T> keyExtractor), thenComparingInt(ToIntFunction<? super T> keyExtractor), thenComparingLong(ToIntFunction<? super T> keyExtractor) :  |
            
> 예제로 사용하지 않았던, 오버로딩 메서드는 설명을 따로 적지 않았습니다. 제가 직접 써보지 않은 메서드는 정보 공유를 하지 않기 위함입니다

- 예제결과

```
intCompareResult = -9
equalsIntComparator = false
equalsIntComparator2 = false
--------------------------------------------------
member = Members{id=0, name='jjm1', phone='010-1234', level=0, items=null}
member = Members{id=1, name='jjm2', phone='010-1234', level=2, items=null}
member = Members{id=2, name='jjm3', phone='010-1234', level=1, items=null}
member = Members{id=3, name='jjm4', phone='010-4321', level=3, items=null}
member = Members{id=4, name='jjm5', phone='010-4321', level=2, items=null}
member = Members{id=5, name='jjm6', phone='010-4321', level=0, items=null}
--------------------------------------------------
sortedLevel = 0
sortedLevel = 0
sortedLevel = 1
sortedLevel = 2
sortedLevel = 2
sortedLevel = 3
--------------------------------------------------
member byLevel = Members{id=0, name='jjm1', phone='010-1234', level=0, items=null}
member byLevel = Members{id=1, name='jjm2', phone='010-1234', level=2, items=null}
member byLevel = Members{id=2, name='jjm3', phone='010-1234', level=1, items=null}
member byLevel = Members{id=3, name='jjm4', phone='010-4321', level=3, items=null}
member byLevel = Members{id=4, name='jjm5', phone='010-4321', level=2, items=null}
member byLevel = Members{id=5, name='jjm6', phone='010-4321', level=0, items=null}
--------------------------------------------------
--------------------------------------------------
natural = Members{id=0, name='jjm1', phone='010-1234', level=0, items=null}
natural = Members{id=1, name='jjm2', phone='010-1234', level=2, items=null}
natural = Members{id=2, name='jjm3', phone='010-1234', level=1, items=null}
natural = Members{id=3, name='jjm4', phone='010-4321', level=3, items=null}
natural = Members{id=4, name='jjm5', phone='010-4321', level=2, items=null}
natural = Members{id=5, name='jjm6', phone='010-4321', level=0, items=null}
--------------------------------------------------
reversed = Members{id=5, name='jjm6', phone='010-4321', level=0, items=null}
reversed = Members{id=4, name='jjm5', phone='010-4321', level=2, items=null}
reversed = Members{id=3, name='jjm4', phone='010-4321', level=3, items=null}
reversed = Members{id=2, name='jjm3', phone='010-1234', level=1, items=null}
reversed = Members{id=1, name='jjm2', phone='010-1234', level=2, items=null}
reversed = Members{id=0, name='jjm1', phone='010-1234', level=0, items=null}
--------------------------------------------------
nullLast = Members{id=0, name='jjm1', phone='010-1234', level=0, items=null}
nullLast = Members{id=1, name='jjm2', phone='010-1234', level=2, items=null}
nullLast = Members{id=2, name='jjm3', phone='010-1234', level=1, items=null}
nullLast = Members{id=3, name='jjm4', phone='010-4321', level=3, items=null}
nullLast = Members{id=4, name='jjm5', phone='010-4321', level=2, items=null}
nullLast = Members{id=5, name='jjm6', phone='010-4321', level=0, items=null}
nullLast = Members{id=6, name='jjm7', phone='010-1234', level=3, items=null}
nullLast = null
--------------------------------------------------
nullFirst = null
nullFirst = Members{id=0, name='jjm1', phone='010-1234', level=0, items=null}
nullFirst = Members{id=1, name='jjm2', phone='010-1234', level=2, items=null}
nullFirst = Members{id=2, name='jjm3', phone='010-1234', level=1, items=null}
nullFirst = Members{id=3, name='jjm4', phone='010-4321', level=3, items=null}
nullFirst = Members{id=4, name='jjm5', phone='010-4321', level=2, items=null}
nullFirst = Members{id=5, name='jjm6', phone='010-4321', level=0, items=null}
nullFirst = Members{id=6, name='jjm7', phone='010-1234', level=3, items=null}
--------------------------------------------------
thenComparedMember = Members{id=0, name='jjm1', phone='010-1234', level=0, items=null}
thenComparedMember = Members{id=2, name='jjm3', phone='010-1234', level=1, items=null}
thenComparedMember = Members{id=1, name='jjm2', phone='010-1234', level=2, items=null}
thenComparedMember = Members{id=6, name='jjm7', phone='010-1234', level=3, items=null}
thenComparedMember = Members{id=5, name='jjm6', phone='010-4321', level=0, items=null}
thenComparedMember = Members{id=4, name='jjm5', phone='010-4321', level=2, items=null}
thenComparedMember = Members{id=3, name='jjm4', phone='010-4321', level=3, items=null}
```

→ 가장 중요한 것은 직접 코드를 짜보되, 예제를 보시고 다른 방식으로도 구현해보시는 것을 추천합니다 → 기술 흡수를 위해

---

## Runnable

- 역할
    - 아무런 객체를 받지 않고, 리턴도 하지 않는ek
    - `() -> void`
    - 실행만 할 수 있는 기능을 담고 있다고 생각하자
    - 인터페이스 코드

    ```java
    package java.lang;
    
    @FunctionalInterface
    public interface Runnable {
        void run();
    }
    ```

- 사용법
    - 인자를 받을 필요도 없고, 어떠한 결과없이 독립적으로 행위만을 담은 코드를 표현할 때 사용하면 될 듯 하다
    - run() 메서드를 통해, 실행 메서드를 동작시킵니다
    - 예제 코드

    ```java
    package functional_interface.basic;
    
    public class RunnableTest {
        public static void main(String[] args) {
            Runnable runnable = ()-> System.out.println("run alone ");
    
            System.out.println(" first print");
            runnable.run();
            System.out.println(" third print");
        }
    }
    ```

- 예제 결과

    ```java
    first print
    run alone 
     third print
    ```

- Runnable 자체로 기능이 특별한 것은 없기 때문에, 간단하게 사용만 해본다

---

## Callable

- 역할
    - 아무런 인자를 받지 않고 `T`타입 객체를 return
    - `() -> T`
    - `Supplier`와 똑같다, 메서드 이름만 다르고 `get()` → `call()` 역할은 똑같다
- `Supplier` VS `Callable`
    - 왜 2개로 나뉘어져 있을까? 활용용도에 맞게 사용하면 됩니다
    - `Callable`은 (`Runnable`)과 함께 병렬 처리(parellel)를 위해 등장했고, `ExecutorService.submit` 메서드의 인자로 `Callable`이 이용됩니다
    - `Supplier`는 해당 타입의 데이터를 "공급"하는 형태, `Callable`은 해당 타입이 "호출가능한" 형태로 사용하면 됩니다
- Callable은 따로 예제를 구현해보지 않습니다!

---

## 2개의 인자를 활용하는 Bi prefix 인터페이스들

- 특정 인자 1개를 받아서 활용하는 `Predicate`, `Consumer`, `Function` 등은 2개 이상의 타입을 받을 수 있는 인터페이스 가 있다
    - `BiPredicate` : `(T, U) -> boolean`,  → Method: `boolean test(T t, U u)`
    - `BiConsumer` : `(T, U) -> void`,  → Method: `boolean accept(T t, U u)`
    - `BiFunction`  : `(T, U) -> R`,  → Method: `boolean apply(T t, U u)`

---

## 기본형 특화 함수형 인터페이스

- 위에서 지금까지 확인한 함수형 인터페이스는 **제네릭 함수형 인터페이스**
- 제네릭의 T,U,R 등의 제네릭 타입에는 참조형만 사용 가능
- 기본형 - 참조형 사이에는 **박싱/언박싱 관계**가 있고, 자바는 **오토박싱으로 자동화 기능이** 있다
    - 지금 이 이야기를 하는 이유는, 이러한 변환 기능들은 비용이 소모(성능, 버그 발생가능성..)
    - 오토박싱으로 인한 비용을 피할 수 있도록 나온 것 → 기본형 특화 함수형 인터페이스
- 그 종류를 알아보자

  ### Predicate( `T -> boolean`)
    - 기본형 → 기본형 리턴
        - `IntPredicate`
        - `LongPredicate`
        - `DoublePredicate`

  ### Consumer( `T -> void` )
    - 기본형 → 기본형 리턴
        - `IntConsumer`
        - `LongConsumer`
        - `DoubleConsumer`

  ### Function( `T -> R`)
    - 기본형 → 기본형 리턴
        - `IntToDoubleFunction`
        - `IntToLongFunction`
        - `LongToDoubleFunction`
        - `LongToIntFunction`
        - `DoubleToLongFunction`
        - `DoubleToIntFunction`
    - 기본형 → R 리턴
        - `IntFunction<R>`
        - `LongFunction<R>`
        - `DoubleFunction<R>`
    - T  → 기본형 리턴
        - `ToIntFunction<T>`
        - `ToDoubleFunction<T>`
        - `ToLongFunction<T>`

  ### Supplier(`()-> T`)
    - 아무것도 받지 않고 → 기본형 리턴
        - `BooleanSupplier`
        - `IntSupplier`
        - `LongSupplier`
        - `DoubleSupplier`

---

## Conclusion

- 람다에서 활용가능한 함수형 인터페이스를 만들어보고, 기본 제공 함수형 인터페이스를 사용해보기도 했다
    - 여기서 만들어본 경험은 함수형 인터페이스를 이해하는 걸로 만족하자
    - 기본 제공 함수형 인터페이스로 대부분의 것을 만족할 수 있기 때문이다, 결국은 어떤 기능을 택해서, 활용할지는 개발자 역량이라고 보면 된다

---


## 참조
- [https://bcp0109.tistory.com/313](https://bcp0109.tistory.com/313) : 뱀귤 블로그
- [https://beomseok95.tistory.com/281?category=1064782](https://beomseok95.tistory.com/281?category=1064782) : Beom Dev Log(Comparator)
- [https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) : java document
- [https://codechacha.com/ko/java8-functional-interface/](https://codechacha.com/ko/java8-functional-interface/) : codechacha.com
- [https://tourspace.tistory.com/9](https://tourspace.tistory.com/9) : Java Comparator