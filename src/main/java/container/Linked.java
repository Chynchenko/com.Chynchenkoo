package container;

public interface Linked <E> {
    void addLastElement (E element);
    void addFirstElement (E element);
    void insertElement (int index, E element);
    void deleteElement (int index);
    int size();

}