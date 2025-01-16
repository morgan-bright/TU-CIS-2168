import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TestSort {

    //code based on SortingDataSample.java written by Ben Nguyen
    public static void main(String... args) throws IOException {
        Random rand = new Random();
        String[] exchanges = {"Sort 1"};
        String[] runTime = {"Sort 1"};
        String[] comparisons = {"Sort 1"};
        appendToCSV("exchanges.csv", exchanges);
        appendToCSV("runTime.csv", runTime);
        appendToCSV("comparisons.csv", comparisons);
        for(int i = 0; i<1000; i++){
            int[] randInt = generateArray(rand.nextInt(50000));
            long timeStart = System.nanoTime();

            Map<String, Integer> compEx = sort1(randInt);
            long runtime = (System.nanoTime() - timeStart) / 1000000;
            String[] data = {String.valueOf(randInt.length), String.valueOf(runtime), compEx.get("Comparisons").toString(), compEx.get("Exchanges").toString()};
            if(i == 0) {
                exchanges = new String[]{"Sort 1", String.valueOf(randInt.length), compEx.get("Exchanges").toString()};
                runTime = new String[]{"Sort 1", String.valueOf(randInt.length), String.valueOf(runtime)};
                comparisons = new String[]{"Sort 1", String.valueOf(randInt.length), compEx.get("Comparisons").toString()};
                appendToCSV("Sort1.csv", data);
                appendToCSV("exchanges.csv", exchanges);
                appendToCSV("runTime.csv", runTime);
                appendToCSV("comparisons.csv", comparisons);
            }else{
                exchanges = new String[]{"", String.valueOf(randInt.length), compEx.get("Exchanges").toString()};
                runTime = new String[]{"", String.valueOf(randInt.length), String.valueOf(runtime)};
                comparisons = new String[]{"", String.valueOf(randInt.length), compEx.get("Comparisons").toString()};
                appendToCSV("Sort1.csv", data);
                appendToCSV("exchanges.csv", exchanges);
                appendToCSV("runTime.csv", runTime);
                appendToCSV("comparisons.csv", comparisons);
            }
        }
        for(int i = 0; i<1000; i++){
            int[] randInt = generateArray(rand.nextInt(50000));
            long timeStart = System.nanoTime();
            int[] arrayCompEx = sort2(randInt, 0, randInt.length-1);
            Map<String, Integer> compEx = new HashMap<>();
            compEx.put("Comparisons", arrayCompEx[0]);
            compEx.put("Exchanges", arrayCompEx[1]);
            long runtime = (System.nanoTime() - timeStart) / 1000000;

            String[] data = {String.valueOf(randInt.length), String.valueOf(runtime), compEx.get("Comparisons").toString(), compEx.get("Exchanges").toString()};
            if(i == 0){
                exchanges = new String[]{"Sort 2", String.valueOf(randInt.length), compEx.get("Exchanges").toString()};
                runTime = new String[]{"Sort 2", String.valueOf(randInt.length), String.valueOf(runtime)};
                comparisons = new String[]{"Sort 2", String.valueOf(randInt.length), compEx.get("Comparisons").toString()};
                appendToCSV("Sort2.csv", data);
                appendToCSV("exchanges.csv", exchanges);
                appendToCSV("runTime.csv", runTime);
                appendToCSV("comparisons.csv", comparisons);
            }else {
                exchanges = new String[]{"", String.valueOf(randInt.length), compEx.get("Exchanges").toString()};
                runTime = new String[]{"", String.valueOf(randInt.length), String.valueOf(runtime)};
                comparisons = new String[]{"", String.valueOf(randInt.length), compEx.get("Comparisons").toString()};
                appendToCSV("Sort2.csv", data);
                appendToCSV("exchanges.csv", exchanges);
                appendToCSV("runTime.csv", runTime);
                appendToCSV("comparisons.csv", comparisons);
            }
        }

        for(int i = 0; i<1000; i++){
            int[] randInt = generateArray(rand.nextInt(50000));
            long timeStart = System.nanoTime();
            Map<String, Integer> compEx = sort3(randInt);
            long runtime = (System.nanoTime() - timeStart) / 1000000;

            String[] data = {String.valueOf(randInt.length), String.valueOf(runtime), compEx.get("Comparisons").toString(), compEx.get("Exchanges").toString()};
            if(i == 0) {
                exchanges = new String[]{"Sort 3", String.valueOf(randInt.length), compEx.get("Exchanges").toString()};
                runTime = new String[]{"Sort 3", String.valueOf(randInt.length), String.valueOf(runtime)};
                comparisons = new String[]{"Sort 3", String.valueOf(randInt.length), compEx.get("Comparisons").toString()};
                appendToCSV("sort3.csv", data);
                appendToCSV("exchanges.csv", exchanges);
                appendToCSV("runTime.csv", runTime);
                appendToCSV("comparisons.csv", comparisons);
            }else{
                exchanges = new String[]{"", String.valueOf(randInt.length), compEx.get("Exchanges").toString()};
                runTime = new String[]{"", String.valueOf(randInt.length), String.valueOf(runtime)};
                comparisons = new String[]{"", String.valueOf(randInt.length), compEx.get("Comparisons").toString()};
                appendToCSV("Sort3.csv", data);
                appendToCSV("exchanges.csv", exchanges);
                appendToCSV("runTime.csv", runTime);
                appendToCSV("comparisons.csv", comparisons);
            }
        }
    }

    public static void appendToCSV(String fileName, String[] data) throws IOException { //source: addison migash
        try (FileWriter csvWriter = new FileWriter(fileName, true)) {
            csvWriter.append(String.join(",", data));
            csvWriter.append("\n");
        }
    }



    public static int[] generateArray(int n){
        int[] randInt = new int[n];
        Random rand = new Random();
        for(int i = 0; i<n; i++){
            randInt[i] = rand.nextInt(100);
        }
        return randInt;
    }

    public static Map<String, Integer> sort1(int[] arr) {
        // source: geeksforgeeks.org
        Map<String, Integer> compEx = new HashMap<>();
        int compares = 0;
        int exchanges = 0;

        for (int gap = arr.length/2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i ++) {
                int currentElement = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > currentElement; j -= gap) {
                    compares ++;
                    exchanges ++;
                    arr[j] = arr[j - gap];
                } // move elements of subarray that are greater than currentElement
                // place currentElement in correct position
                exchanges ++;
                arr[j] = currentElement;
            }
        }
        compEx.put("Comparisons", compares);
        compEx.put("Exchanges", exchanges);
        return compEx;

    }
    public static int[] partition(int[] arr, int low, int high) { // source: geeksforgeeks.org
        int compares = 0;
        int exchanges = 0;
        int pivot = arr[high];
        int left = low - 1;
        for (int next = low; next < high; next++) {
            compares ++;

            if (arr[next] < pivot) { // if current element is smaller than pivot
                left ++;
                // swap arr[i] and arr[j]
                int temp = arr[left];
                arr[left] = arr[next];
                arr[next] = temp;
                exchanges += 2;
            }
        }
        // swap the pivot with the element at index 'i + 1'
        int temp = arr[left + 1];
        arr[left + 1] = arr[high];
        arr[high] = temp;
        exchanges += 2;

        return new int[]{left + 1, compares, exchanges};
    }
    public static int[] sort2(int[] arr, int low, int high) { // source: geeksforgeeks.org
        if (low < high) {
            int[] partitionResult = partition(arr, low, high);
            int pi = partitionResult[0];
            int compares = partitionResult[1];
            int exchanges = partitionResult[2];

            int[] leftResults = sort2(arr, low, pi - 1);
            int[] rightResults = sort2(arr, pi + 1, high);

            compares += leftResults[0] + rightResults[0];
            exchanges += leftResults[1] + rightResults[1];

            return new int[]{compares, exchanges};
        } else {
            return new int[]{0, 0};  // Base case, no operations needed
        }
    }

    public static Map<String, Integer> sort3(int[] data) {Map<String, Integer> compEx = new HashMap<>();
        int compares = 0;
        int exchanges = 0;
        for (int next = 1; next < data.length; next++) {
            Integer nextVal = data[next]; // element to insert

            while (next > 0 && nextVal.compareTo(data[next - 1]) < 0) {
                compares ++;
                data[next] = data[next - 1]; // shift left
                next--;
            }
            exchanges ++;
            data[next] = nextVal; // insert nextVal at next
        }

        // insertion sort implementation
        compEx.put("Comparisons", compares);
        compEx.put("Exchanges", exchanges);
        return compEx;
    }
}












