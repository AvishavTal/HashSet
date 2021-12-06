import java.util.Collection;

import java.util.TreeSet;


/**
 * Wraps an underlying Collection and serves to both simplify its API and give it a common type with the
 * implemented SimpleHashSets.
 * @author avishavtal
 */
public class CollectionFacadeSet implements SimpleSet {
    protected Collection<String> collection;

    /**
     * Creates a new facade wrapping the specified collection.
     *
     * @param collection The Collection to wrap.
     */
    public CollectionFacadeSet(Collection<String> collection) {

        this.collection = collection;
        deleteDuplicates();
    }

    /* delete the duplicates from the collection.*/
    private void deleteDuplicates() {
        TreeSet<String> temp = new TreeSet<>();
        temp.addAll(collection);
        collection.clear();
        collection.addAll(temp);



    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        return !collection.contains(newValue) && collection.add(newValue);
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        return collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        return collection.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return collection.size();
    }

}

