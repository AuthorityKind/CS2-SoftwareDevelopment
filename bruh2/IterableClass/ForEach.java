import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForEach implements Iterable<Integer> {
    private List<Integer> numList = new ArrayList<Integer>();

    public ForEach(Integer min, Integer max) {
        for (Integer i = min; i <= max; i++) {
            numList.add(i);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return numList.iterator();
    }
}