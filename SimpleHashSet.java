/**
 *  A superclass for implementations of hash-sets implementing the SimpleSet interface.
 *  @author avishavtal
 */
public abstract class SimpleHashSet implements SimpleSet {
    /**
     * Describes the higer load factor of a newly created hash set
     */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
    /**
     * Describes the lower load factor of a newly created hash set
     */
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    /**
     * Describes the capacity of a newly created hash set
     */
    protected static final int INITIAL_CAPACITY = 16;
    /*upper load factor of this set.*/
    private float upperLoadFactor;
    /*lower load factor of this set.*/
    private float lowerLoadFactor;

    // counter of the elements in the set.
    private int size;


    /**
     *  Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY
     *  and DEFAULT_HIGHER_CAPACITY
     */
    protected SimpleHashSet(){

        upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        lowerLoadFactor = DEFAULT_LOWER_CAPACITY;

    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     * @param upperLoadFactor the upper load factor before rehashing
     * @param lowerLoadFactor the lower load factor before rehashing
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;

    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     * (see the exercise description for details)
     * @param index the index before clamping
     * @return an index properly clamped
     */
    protected int clamp(int index){
        return clampHelper(index, null);

    }
    /* find the index of the string if given, else return the location to put ut there.  */
    private int clampHelper(int index, String searchVal){
        int i = 0;
        int newIndex;
        while (i < capacity()){
            newIndex = (index+(i+i*i)/2)&(capacity()-1);
            if (searchVal != null) {

                    if (isThere(newIndex, searchVal)) {
                        return newIndex;
                    } else if (isFreeCell(newIndex)&&!isDelete(newIndex)){
                        return -1;
                    }


            } else if (isFreeCell(newIndex)){
                return newIndex;
            }
            i++;
        }
        return -1;
    }

    /**
     * check if was value in the cell with the given index.
     *
     * @param newIndex the index of the cell we want to check.
     * @return true iff was value in the cell with the given index and we care about it.
     */
    protected boolean isDelete(int newIndex) {
        return false;
    }

    /**
     * check if the given value in the cell with the given index.
     * @param newIndex the index we want to check.
     * @param searchVal the value we want to check.
     * @return true if the given value in the cell with the given index, false otherwise.
     */
    protected abstract boolean isThere(int newIndex, String searchVal);

    /**
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor(){
        return lowerLoadFactor;
    }

    /**
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor(){
        return upperLoadFactor;
    }

    /**
     * @return  The current capacity (number of cells) of the table.
     */
    public abstract int capacity();

    /**
     * check if the cell with the given index can get more value.
     * @param index the index of the cell we check.
     * @return true if the cell with the given index can get more value, false otherwise.
     */
    protected abstract boolean isFreeCell(int index);


    /**
     * Look for a specified value in the set.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (contains(newValue)){
            return false;
        }
        if ((float)(size+1)/capacity() > upperLoadFactor){
            reHash(false);
        }
        int index = clamp(newValue.hashCode());
        insert(newValue, index);
        size++;
        return true;
    }

    /**
     * @param searchVal Value to search for.
     * @return true if searchVal in the set, false otherwise.
     */
    @Override
    public boolean contains(String searchVal) {
        int index = searchVal.hashCode();
        int newIndex = clampHelper(index, searchVal);
        return newIndex >= 0;
    }


    /**
     * insert the given value to the set.
     * @param newValue New value to add to the set.
     * @param index the index of the new value in the table.
     */
    protected abstract void insert(String newValue, int index);


    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete.
     * @return True iff toDelete is found and deleted.
     */
    @Override
    public boolean delete(String toDelete) {
        int index = toDelete.hashCode();
        int newIndex = clampHelper(index,toDelete);
        if (newIndex < 0){
            return false;
        }
        remove(toDelete, newIndex);
        size--;
        if (((float)size/capacity()) < lowerLoadFactor){
            reHash(true);
        }
        return true;

    }


    /**
     * remove the given string from the table.
     * @param toDelete value to delete.
     * @param newIndex the index of toDelete in the table.
     */
    protected abstract void remove(String toDelete, int newIndex);

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * rehash the set.
     * @param toSmaller true if need smaller table, false otherwise.
     */
    protected abstract void reHash(boolean toSmaller);

    /**
     * add to the set all the strings in the given array.
     * @param data array of strings to add.
     */
    protected void addAll(String[] data){
        for (String string: data){
            add(string);
        }
    }
}
