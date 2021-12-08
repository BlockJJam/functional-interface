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
        Members superMember = superMembersSupplier.get();
        System.out.println("superMember = " + superMember);

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
