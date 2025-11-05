package ejercicio7clase8;

public class CustomStack<T> {
    private static class StackNode<T> {
        T data;
        StackNode<T> next;

        StackNode(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private StackNode<T> top;
    private int size;

    public CustomStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(T data) {
        StackNode<T> newNode = new StackNode<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
