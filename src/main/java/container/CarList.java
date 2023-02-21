package container;
import model.Car;
import model.PassengerCar;

import java.util.Iterator;
public class CarList {
    public static void main(String[] args) {
        LinkedlistRealization<Car> linkedRealization = new LinkedlistRealization<>();
        PassengerCar passengerCar1 = new PassengerCar();
        PassengerCar passengerCar2 = new PassengerCar();
        PassengerCar passengerCar3 = new PassengerCar();
        PassengerCar passengerCar4 = new PassengerCar();
        PassengerCar passengerCar5 = new PassengerCar();
        linkedRealization.addFirstElement(passengerCar1);
        linkedRealization.addFirstElement(passengerCar2);
        linkedRealization.addFirstElement(passengerCar3);
        linkedRealization.addFirstElement(passengerCar4);
        linkedRealization.addFirstElement(passengerCar5);
        System.out.println("Size before: " + linkedRealization.size());
        for (Car car : linkedRealization) {
            System.out.println("Asc ->" + car.getId());
        }

        System.out.println("Car insert: " + passengerCar3);
        linkedRealization.insertElement(4, passengerCar3);
        System.out.println("Size after insert: " + linkedRealization.size());

        for (Car car : linkedRealization) {
            System.out.println("Asc ->" + car.getId());
        }
        System.out.println("Car index delete: " + 4);
        linkedRealization.deleteElement(4);
        System.out.println("Size after delete: " + linkedRealization.size());
        for (Car car : linkedRealization) {
            System.out.println("Asc ->" + car.getId());
        }

        Iterator<Car> iterator = linkedRealization.Iterator();
        while (iterator.hasNext()) {
            System.out.println("Desc ->" + iterator.next());
        }
    }
}

class LinkedlistRealization<E extends Car> implements Linked<E>, Iterable<E>, CarIterator<E> {
    private Node<E> firstNode;
    private Node<E> lastNode;
    private int size = 0;


    public LinkedlistRealization() {
        lastNode = new Node<>(null, firstNode, null);
        firstNode = new Node<>(null, null, lastNode);
    }

    @Override
    public void addLastElement(final E element) {
        Node<E> prev = lastNode;
        prev.setElement(element);
        lastNode = new Node<>(null, prev, null);
        prev.setNextElement(lastNode);
        size++;
    }

    @Override
    public void addFirstElement(final E element) {
        Node<E> next = firstNode;
        next.setElement(element);
        firstNode = new Node<>(null, null, next);
        next.setPreviousElement(firstNode);
        size++;
    }

    public int indexOfElement(final E element) {
        for (int i = 0; i < size(); i++) {
            if (getElementByIndex(i).getId().equals(element.getId())) {
                return i;
            }
        }
        return 0;
    }

    public E getElementByIndex(final int counter) {
        Node<E> target = firstNode.getNextElement();
        for (int i = 0; i < counter; i++) {
            target = getNextElement(target);
        }
        return target.getElement();
    }

    private Node<E> getNodeByIndex(final int counter) {
        Node<E> target = firstNode.getNextElement();
        for (int i = 0; i < counter; i++) {
            target = getNextElement(target);
        }
        return target;
    }

    private Node<E> getNextElement(Node<E> current) {
        return current.getNextElement();
    }


    @Override
    public void insertElement(final int index,final  E element) {
        if (index == 0) {
            addFirstElement(element);
        }
        if (index == size()) {
            addLastElement(element);

        } else {
            Node<E> node = getNodeByIndex(index);
            Node<E> newNode = new Node<>(element, node.getPreviousElement(), node.getNextElement());
            Node<E> prevNode = node.previousElement;
            newNode.setElement(element);
            newNode.nextElement = node;
            newNode.previousElement = prevNode;
            prevNode.nextElement = newNode;
            node.previousElement = newNode;
            size++;
        }
    }

    @Override
    public void deleteElement(final int index) {
        Node<E> node = getNodeByIndex(index);
        Node<E> prevNode = node.previousElement;
        Node<E> nextNode = node.nextElement;
        if (size == 1) {
            firstNode.nextElement = lastNode;
            lastNode.previousElement = firstNode;
            size--;
        } else if (index == 0) {
            firstNode.nextElement = nextNode;
            nextNode.previousElement = null;
            node.nextElement = null;
            size--;
        } else if (index == size() - 1) {
            lastNode.previousElement = prevNode;
            prevNode.nextElement = null;
            node.previousElement = null;
            size--;
        } else {
            prevNode.nextElement = nextNode;
            nextNode.previousElement = prevNode;
            node.nextElement = null;
            node.previousElement = null;
            size--;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < size;
            }

            @Override
            public E next() {
                return getElementByIndex(counter++);
            }
        };
    }


    @Override
    public Iterator<E> Iterator() {
        return new Iterator<E>() {
            int counter = size - 1;

            @Override
            public boolean hasNext() {
                return counter >= 0;
            }

            @Override
            public E next() {
                return getElementByIndex(counter--);
            }
        };
    }

    static class Node<E extends Car> {
        private Node<E> previousElement;
        private Node<E> nextElement;
        private E element;
        public static int size;

        public Node(E element, Node<E> previousElement, Node<E> nextElement) {
            this.element = element;
            this.previousElement = previousElement;
            this.nextElement = nextElement;
        }

        public Node<E> getPreviousElement() {
            return previousElement;
        }

        public void setPreviousElement(Node<E> previousElement) {
            this.previousElement = previousElement;
        }

        public Node<E> getNextElement() {
            return nextElement;
        }

        public void setNextElement(Node<E> nextElement) {
            this.nextElement = nextElement;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }
    }
}