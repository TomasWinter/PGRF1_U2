package sort;

import java.util.Collections;
import java.util.List;

public class SelectionSort {

    public static <T extends Comparable<T>> void sort(List<T> list) {
        // selection sort vybere nejmenší element a dá ho na začátek neseřazené části listu
        for (int i = 0; i < list.size(); i++) {
            T minimum = list.get(i);
            int minimumIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareTo(minimum) < 0) {
                    minimum = list.get(j);
                    minimumIndex = j;
                }
            }
            Collections.swap(list, i, minimumIndex);
        }
    }

}
