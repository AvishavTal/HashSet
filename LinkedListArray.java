import java.util.LinkedList;

public class LinkedListArray {
    CollectionFacadeSet[] array;
    LinkedListArray(int size){
        array = new CollectionFacadeSet[size];
        for (int i = 0; i < size; i++){
            array[i] = new CollectionFacadeSet(new  LinkedList<String>());
        }

    }
}
