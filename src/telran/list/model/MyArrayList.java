package telran.list.model;

import telran.list.interfaces.IList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

public class MyArrayList<E> implements IList<E> {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private Object[] elements;
    private int size;

    public MyArrayList() {
        this.elements = new Object[10];
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal capacity = " + initialCapacity);
        }
        if (initialCapacity > MAX_ARRAY_SIZE) {
            initialCapacity = MAX_ARRAY_SIZE;
        }
        this.elements = new Object[initialCapacity];
    }

    //O(1)
    @Override
    public int size() {
        return size;
    }

    //O(n)
    @Override
    public boolean add(int index, E element) {
        ensureCapacity(); //O(n)
        if (index == size) {
            elements[size++] = element;
            return true;
        }
        checkIndex(index);
        System.arraycopy(elements, index, elements, index + 1, size++ - index); //O(1)
        elements[index] = element;
        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    //O(n)
    @Override
    public int indexOf(Object o) {

        for (int i = 0; i < size; i++) {
            if (getPredicate(o).test((E) elements[i])) {
                return i;
            }
        }
        return -1;
    }

    //O(n)
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {

            if (getPredicate(o).test((E) elements[i])) {
                return i;
            }
        }
        ;

        return -1;
    }

    //O(1)
    @Override
    public E remove(int index) {
        checkIndex(index);
        E tmp = (E) elements[index];
        System.arraycopy(elements, index + 1, elements, index, size-- - index);
        return tmp;
    }

    //O(1)
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < size();
            }

            @Override
            public E next() {
                return (E) elements[counter++];
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    //O(n)
    private void ensureCapacity() {
        if (size == MAX_ARRAY_SIZE) {
            throw new OutOfMemoryError();
        }
        if (size == elements.length) {
            int newCapacity = elements.length + (elements.length / 2);
            if (newCapacity > MAX_ARRAY_SIZE || newCapacity < 0) {
                newCapacity = MAX_ARRAY_SIZE;
            }
            elements = Arrays.copyOf(elements, newCapacity); //O(n)
        }
    }

    //O(1)
    @Override
    public E set(int index, Object o) {
        checkIndex(index);
        E tmp = (E) elements[index];
        elements[index] = o;
        return tmp;
    }


    //O(n)
    @Override
    public void clear(){
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    private Predicate<E> getPredicate(Object o) {
        return (E currElement) -> ((o != null && o.equals(currElement))
                || (o == null && o == currElement));
    }
}
