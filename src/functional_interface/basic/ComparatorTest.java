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

        @Override
        public int compareTo(Members member) {
            return Integer.compare(this.getId(), member.getId());
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

        public Integer getLevel() {
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

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
