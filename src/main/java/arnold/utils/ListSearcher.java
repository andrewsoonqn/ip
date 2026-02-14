package arnold.utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A utility class for searching through lists using a specified condition.
 *
 * @param <T> The type of elements in the list.
 */
public class ListSearcher<T> {
    /**
     * Filters the elements of the given list based on the specified predicate and returns a new list
     * containing only the elements that satisfy the predicate condition.
     *
     * @param list The list of elements to filter. Must not be null.
     * @param predicate The condition to apply to each element in the list. Must not be null.
     * @return A new list containing elements that match the predicate condition.
     *     Returns an empty list if no elements satisfy the predicate
     *     or if the input list is empty.
     * @throws NullPointerException If the input list or predicate is null.
     */
    public List<T> findItems(List<T> list, Predicate<T> predicate) {
        Stream<T> filteredStream = list.stream().filter(predicate);
        return filteredStream.toList();
    }
}
