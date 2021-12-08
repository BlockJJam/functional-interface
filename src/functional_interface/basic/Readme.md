# 기본적으로 제공하는 Functional Interface

생성일: 2021년 11월 29일 오후 11:36

## 람다식으로 많이 사용하는 기본적인 Functional Interface 종류

### **함수형 인터페이스 - Description - Method**

- Predicate - `T -> boolean` - `boolean test(T t)`

- Consumer - `T -> void` - `void accept(T t)`

- Supplier - `() -> T` - `T get()`

- Function<T,R> - `T -> R` - `R apply(T t)`

- Comparator - `(T, T) -> int` - `int compare(T o1, T o2)`

- Runnable - `() -> void` - `void run()`

- Callable - `() -> T` - `V call()`


- 이외에도 많이 있다, 어느 정도 있나 확인만 해볼까?

![Untitled](https://user-images.githubusercontent.com/57485510/144847548-6fe1b5cb-5e34-4c75-8a2d-2893bb8b8393.png)


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