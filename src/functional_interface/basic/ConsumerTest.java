package functional_interface.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerTest {
    public static void main(String[] args) {
        Consumer<String> printString = text -> System.out.println("hello "+ text + "?");
        printString.accept("Block");

        Consumer<String> printString2 = text -> System.out.println("Wow "+ text);
        printString.andThen(printString2).accept("Block");

        Members member1 = new Members(0,"jjm","01022223333", 1, Arrays.asList("potion","sword"));

        Consumer<Members> dieToStart = members -> {
          members.setLevel(0);
          members.clearItems();
        };

        System.out.println("member1 spec: " +member1);
        dieToStart.accept(member1);
        System.out.println("member1 after die spec: " + member1);

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
