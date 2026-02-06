package arnold.utils;

import arnold.utils.ListSearcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListSearcherTest {

        private final ListSearcher<Integer> intSearcher = new ListSearcher<>();

        @Test
        void findItems_filtersMatchingItems() {
            List<Integer> input = List.of(1, 2, 3, 4, 5);

            List<Integer> result = intSearcher.findItems(input, n -> n % 2 == 0);

            assertEquals(List.of(2, 4), result);
        }

        @Test
        void findItems_noMatches_returnsEmptyList() {
            List<Integer> input = List.of(1, 3, 5);

            List<Integer> result = intSearcher.findItems(input, n -> n % 2 == 0);

            assertTrue(result.isEmpty());
        }

        @Test
        void findItems_inputEmpty_returnsEmptyList() {
            List<Integer> input = List.of();

            List<Integer> result = intSearcher.findItems(input, n -> true);

            assertTrue(result.isEmpty());
        }

        @Test
        void findItems_allMatch_returnsAllItems() {
            List<Integer> input = List.of(1, 2, 3);

            List<Integer> result = intSearcher.findItems(input, n -> n > 0);

            assertEquals(input, result);
        }

        @Test
        void findItems_doesNotMutateOriginalList() {
            List<Integer> input = new ArrayList<>(List.of(1, 2, 3, 4));

            List<Integer> result = intSearcher.findItems(input, n -> n > 2);

            assertEquals(List.of(1, 2, 3, 4), input, "Original list should remain unchanged");
            assertEquals(List.of(3, 4), result);
        }

        @Test
        void findItems_resultListIsUnmodifiable() {
            List<Integer> input = List.of(1, 2, 3);

            List<Integer> result = intSearcher.findItems(input, n -> true);

            assertThrows(UnsupportedOperationException.class, () -> result.add(999));
        }

        @Test
        void findItems_listIsNull_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () ->
                    intSearcher.findItems(null, n -> true)
            );
        }

        @Test
        void findItems_predicateIsNull_throwsNullPointerException() {
            List<Integer> input = List.of(1, 2, 3);

            assertThrows(NullPointerException.class, () ->
                    intSearcher.findItems(input, null)
            );
        }
    }
