import java.util.Iterator;

/**
 * implements open hash set.
 * @author avishavtal
 */
public class OpenHashSet extends SimpleHashSet {
    // hold the lists tht hold the elements of this set.
    private LinkedListFacadeSet[] table;

    /**
     * A default constructor. Constructs a new, empty table with default values of capacity and load factors.
     */
    public OpenHashSet() {
        super();
        setTable(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity.
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param loweLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float loweLoadFactor){
        super(upperLoadFactor, loweLoadFactor);
        setTable(INITIAL_CAPACITY);

    }

    @Override
    protected boolean isDelete(int newIndex) {
        return false;
    }


    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data){
        super();
        setTable(INITIAL_CAPACITY);
        addAll(data);

    }
    /*create new table with the given size and insert linked list to each cell. */
    private void setTable(int size){
        table = new LinkedListFacadeSet[size];
        for (int i = 0; i < size; i++){
            table[i] = new LinkedListFacadeSet();
        }
    }


    @Override
    public int capacity() {
        return table.length;
    }

    @Override
    protected boolean isFreeCell(int index) {
        return true;
    }


    @Override
    protected void insert(String newValue, int index) {
        table[index].add(newValue);
    }

    @Override
    protected void remove(String toDelete, int newIndex) {
        table[newIndex].delete(toDelete);

    }



    @Override
    protected void reHash(boolean toSmaller) {
        LinkedListFacadeSet[] old = table;
        LinkedListFacadeSet linkedListFacadeSet;
        int oldCapacity = capacity();
        if (toSmaller){
            int newCapacity =(int) oldCapacity/2;
            if (newCapacity<1){
                newCapacity=1;
            }
            setTable(newCapacity);
        } else {
            setTable(oldCapacity*2);
        }
        Iterator<String>  iterator;
        String string;
        int index;
        for (int i = 0; i < oldCapacity; i++){
            linkedListFacadeSet = old[i];
            iterator = linkedListFacadeSet.iterator();
            while (iterator.hasNext()){
                string = iterator.next();
                index = clamp(string.hashCode());
                insert(string, index);
            }
        }


    }

    @Override
    protected boolean isThere(int newIndex, String searchVal) {
        return table[newIndex].contains(searchVal);
    }

}
