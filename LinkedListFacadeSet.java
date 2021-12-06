import java.util.Iterator;
import java.util.LinkedList;

/**
 * warps a linked list so it can be use in the open hash set.
 * @author avishavtal
 */
public class LinkedListFacadeSet extends CollectionFacadeSet implements Iterable {


    /**
     * Creates a new facade wrapping the linked list.
     */
    public LinkedListFacadeSet() {
        super(new LinkedList<>());
    }

    @Override
    public Iterator<String> iterator() {
        return collection.iterator();
    }

    @Override
    public boolean add(String newValue) {
        return collection.add(newValue);
    }
}
