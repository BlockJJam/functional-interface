package functional_interface.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FunctionTest {
    public static void main(String[] args) {
        Function<Integer, Double> divide = (value) -> Double.valueOf(value / 3.0f);
        
        Double result = divide.apply(5);
        System.out.println("result = " + result);

        Function<Members, Double> multiplyLevel = (members) -> Double.valueOf(members.getLevel() * 1.5f);
        Function<Double, String> convertDoubleToString = (num)-> String.valueOf(num);
        Members newMember = new Members(0,"blockjjam","01013134545", 2, Arrays.asList("1","2"));

        // second.compose( Function first) <- Function first가 먼저 호출
        String composedLevel = convertDoubleToString.compose(multiplyLevel).apply(newMember);
        System.out.println("composedLevel = " + composedLevel);

        // first.andThen( Function second) <- Function second가 먼저 호출
        String andThenLevel = multiplyLevel.andThen(convertDoubleToString).apply(newMember);
        System.out.println("andThenLevel = " + andThenLevel);

        // 입력받는 객체 그대로를 return 해주는 정체성 확인 메서드? 같은 느낌, 용도는 잘 모르겠다;;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public List<String> getItems() {
            return items;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void addItem(String item){
            this.items.add(item);
        }

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
