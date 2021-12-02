### Functional Interface의 정의는 간단하다

- 잠깐 Functional Interface를 알아보기 전에!
    - java 8 ~ : 기본 구현체를 포함해서 default 메서드를 포함할 수 있게 되었다

    ```java
    public interface Sample{
    		List<String> real();
    		List<String> fake();
    
    		public default String sampleMethod( String realStr ){
    				return realStr;
    		}
    }
    ```

- Functional Interface 는 무엇을 의미할까?
    - 여러개의 default 메서드가 있을 순 있다, **다만 하나의 추상메서드**만 가진다면
      → 함수형 인터페이스(functional interface)
    - **자바의 람다 표현식**은 함수형 인터페이스로만 사용이 가능
    - `@FunctionalInterface` 애너테이션은?
      → 무조건 붙여줘야 하진 않지만, 무조건 붙여주자
      → 다만, 인터페이스를 검증해주고, 유지보수를 위한 목적

      ![Untitled](https://user-images.githubusercontent.com/57485510/144428889-cdcc8e68-cae7-4f7d-97bc-c34224ae879e.png)

      → default method( + static method)가 아니면 `@FunctionalInterface`에 의해 검증이 되는 모습


### Functional Interface를 Custom하게 만들고 이용해보자

- Functional Interface를 직접 짜보자

```java
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
```

- 내가 만든 인터페이스를 사용해보자

```java
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
```

- 결과화면

```
Hello Custom Abstract Method
Hello Default!
Hello Static
```

### Functional Interface 직접 구현해보면,

- 직접 커스텀 인터페이스를 만들 일이 많이 있진 않을 것이다
  → 공부의 목적도 인터페이스를 만들기 위함이 아니였다
- 람다를 쓰기 위해선 Functional Interface를 써야 한다고 했는데?
  → java에서 기본적으로 제공하는 Functional Interface로 대부분 커버가 가능하다

### 다음화: Java에서 제공해주는 기본 Functional Interface를 알아보기!

---

### 참조

[https://bcp0109.tistory.com/313](https://bcp0109.tistory.com/313) : Java 8 함수형 인터페이스

[https://codechacha.com/ko/java8-functional-interface/](https://codechacha.com/ko/java8-functional-interface/) : **Java8 - 함수형 인터페이스(Functional Interface) 이해하기**

[https://docs.oracle.com/javase/specs/jls/se17/html/jls-9.html#jls-9.8](https://docs.oracle.com/javase/specs/jls/se17/html/jls-9.html#jls-9.8) : java 공식 reference