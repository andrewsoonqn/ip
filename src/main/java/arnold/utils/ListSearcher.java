package arnold.utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ListSearcher<T> {
    public List<T> findItems(List<T> list, Predicate<T> predicate) {
        Stream<T> filteredStream = list.stream().filter(predicate);
        return filteredStream.toList();
    }
}
