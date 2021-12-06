import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * this class analyze the performance of various implementations of simple set.
 * @author avishavtal
 */
public class SimpleSetPerformanceAnalyzer {
    /* operations symbols */
    private static final int ADD = 0;
    private static final int CONTAINS = 3;
    /* data structures symbols*/
    private static final int OPEN_HASH_SET = 3;
    private static final int CLOSED_HASH_SET = 2;
    private static final int TREE_SET = 1;
    private static final int HASH_SET = 0;
    private static final int LINKED_LIST = 4;
    // before each measurement this sets will do the operation for warm up.
    private static SimpleSet[] warmUpSets = new SimpleSet[5];
    //strings will do the operations on this strings for warm up.
    private static String[] warmUpArray;
    /* arrays of data for measurement*/
    private static final String[] data1 = Ex3Utils.file2array("data1.txt");
    private static final String[] data2 = Ex3Utils.file2array("data2.txt");
    // implementations of simple set to analyze their performance.
    private static SimpleSet[] sets = new SimpleSet[5];
    // initialize the sets.
    private static void setSets(SimpleSet[] simpleSets){
        simpleSets[OPEN_HASH_SET] = new OpenHashSet();
        simpleSets[CLOSED_HASH_SET] = new ClosedHashSet();
        simpleSets[TREE_SET] = new CollectionFacadeSet(new TreeSet<>());
        simpleSets[HASH_SET] = new CollectionFacadeSet(new HashSet<>());
        simpleSets[LINKED_LIST] = new CollectionFacadeSet(new LinkedList<>());
    }
    // initialize the warm up array
    private static void setWarmUpArray(){
        warmUpArray = new String[data1.length];
        for (int i = 0; i < data1.length; i++){
            if (i % 2 == 0){
                warmUpArray[i] = data2[i];
            } else {
                warmUpArray[i] = data1[i];
            }
        }
    }
    // measure the time it takes to add the given data to each set.
    private static void measureAdd(String[] data){
        System.out.println("add results:");
        System.out.println();
        for (int i =0 ; i < 5; i++){
            warmUp(i, ADD);
            long before = System.nanoTime();
            for (String string: data){
                sets[i].add(string);
            }
            long result = (System.nanoTime() - before)/1000000;
            String type = getType(i);
            System.out.println(type+": "+result);

        }
        System.out.println();
    }
    // measure the time it takes to search the given word in each set.
    private static void measureContains(String word){
        System.out.println("contains "+word+" results:");
        for (int i = 0; i < 5; i++){
            warmUp(i, CONTAINS);
            int iterations = 0;
            long before = System.nanoTime();
            while ((System.nanoTime()-before)/1000000000 < 5){
                iterations++;
                sets[i].contains(word);
            }
            long result = (System.nanoTime()-before)/iterations;
            System.out.println(getType(i)+": "+result);

        }
        System.out.println();
    }
    // do the warm up before the measurement.
    private static void warmUp(int i, int operation) {


        if (operation == ADD){
            for (String string: warmUpArray){
                warmUpSets[i].add(string);
            }


        } else {
            for (String string: warmUpArray){
                warmUpSets[i].contains(string);
            }
        }





    }

    private static String getType(int i) {
        switch (i){
            case OPEN_HASH_SET:
                return "OpenHashSet";
            case CLOSED_HASH_SET:
                return "ClosedHashSet";
            case TREE_SET:
                return "TreeSet";
            case HASH_SET:
                return "HashSet";
            case LINKED_LIST:
                return "LinkedList";
        }
        return null;
    }
    public static void main(String[] args){
        setSets(sets);
        setSets(warmUpSets);
        setWarmUpArray();
        measureAdd(data1);
        measureContains("hi");
        measureContains("-13170890158");
        setSets(sets);
        setSets(warmUpSets);
        measureAdd(data2);
        measureContains("23");
        measureContains("hi");
    }

}
