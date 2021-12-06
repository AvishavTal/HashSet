/**
 * implement closed hash set.
 * @author avishavtal
 */
public class ClosedHashSet extends SimpleHashSet{
    private String[] table = new String[INITIAL_CAPACITY];
    private boolean[] deleted = new boolean[INITIAL_CAPACITY];


    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){
        super();

    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor,lowerLoadFactor);


    }

    /**
     * @param newIndex the index of the cell we want to check.
     * @return true iff was value in the cell with the given index.
     */
    @Override
    protected boolean isDelete(int newIndex) {
        return deleted[newIndex];
    }


    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data){
        super();
        addAll(data);
    }


    @Override
    public int capacity() {
        return table.length;
    }

    @Override
    protected boolean isFreeCell(int index) {
        return table[index] == null;
    }

    @Override
    protected void insert(String newValue, int index) {
        table[index] = newValue;
        deleted[index] = false;

    }

    @Override
    protected boolean isThere(int newIndex, String searchVal) {
        return table[newIndex] != null && table[newIndex].equals(searchVal);

    }



    @Override
    protected void remove(String toDelete, int newIndex) {
        table[newIndex] = null;
        deleted[newIndex] = true;

    }



    @Override
    protected void reHash(boolean toSmaller) {
        double factor = 2;
        if (toSmaller){
            factor = 0.5;
        }
        String[] old = table;
        int oldCapacity = capacity();
        int newCapacity = (int) (oldCapacity*factor);
        if (newCapacity<1){
            newCapacity = 1;
        }
        table = new String[newCapacity];
        deleted = new boolean[newCapacity];
        for (int i = 0; i < oldCapacity; i++){
            if (old[i] != null){
                insert(old[i], clamp(old[i].hashCode()));
            }
        }
    }


}
