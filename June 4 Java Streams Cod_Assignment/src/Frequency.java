
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Frequency {
    public static void main(String[] args) {
        String s = "ilovejavatech";

        List<String> strList = new ArrayList<>();
        Arrays.stream(s.split("")).collect(Collectors.groupingBy(s1->s1));
        LinkedHashMap<String,Long> linkedHashMap = Arrays.stream(s.split("")).collect(Collectors.groupingBy(Function.identity(),LinkedHashMap::new, Collectors.counting()));
        System.out.println(linkedHashMap);
    }

}
