import java.util.*;

public class Task1 {

    public static void main(String[] args) {
        List<Integer> input = new ArrayList<>(Arrays.asList(1,2,3,0,-10,1,3,0,3));
        getCount(input).forEach(e -> System.out.println("number " + e.getKey() + " count=" + e.getValue()));
    }

    public static List<Map.Entry<Integer,Integer>> getCount(List<Integer> input) {
        TreeMap<Integer,Integer> array = new TreeMap<>();
        for(Integer num : input) {
            Integer count = array.get(num);
            array.put(num, count == null ? 1 : ++count);
        }
        List<Map.Entry<Integer,Integer>> output = new ArrayList(array.entrySet());
        output.sort((e1,e2) -> e2.getValue().compareTo(e1.getValue()));
        return output;
    }

}
