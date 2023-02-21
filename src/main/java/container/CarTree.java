package container;

import model.Car;

public class CarTree<T extends Car> extends CarComparator<T> {
    protected Node<T> root;

    public void insert(T data) {
        root = insert(root, data);
    }

    private Node<T> insert(Node<T> root, T data) {
        if (root == null) {
            return new Node<T>(data);
        } else if (compare(root, data) < 0) {
            root.left = insert(root.left, data);
        } else if (compare(root, data) > 0) {
            root.right = insert(root.right, data);
        }
        return root;
    }

    public void delete(T data) {
        root = delete(root, data);
    }

    private Node<T> delete(Node<T> root, T data) {
        if (root == null) {
            return null;
        } else if (compare(root, data) < 0) {
            root.left = delete(root.left, data);
        } else if (compare(root, data) > 0) {
            root.right = delete(root.right, data);
        } else {

            if (root.left == null && root.right == null) {
                return null;
            } else if (root.right == null) {
                return root.left;
            } else if (root.left == null) {
                return root.right;
            } else {
                root.data = findMax(root.left);
                root.left = delete(root.left, root.data);
            }
        }

        return root;
    }

    private T findMax(Node<T> root) {
        while (root.right != null) {
            root = root.right;
        }
        return root.data;
    }

    public boolean contains(T data) {
        return contains(root, data);
    }

    private boolean contains(Node<T> root, T data) {
        if (root == null) {
            return false;
        } else if (compare(root, data) < 0) {
            return contains(root.left, data);
        } else if (compare(root, data) > 0) {
            return contains(root.right, data);
        } else {
            return true;
        }
    }

    public void inorder() {
        System.out.print("In-order:");
        inorder(root);
        System.out.println();
    }

    private void inorder(Node<T> root) {
        if (root == null) return;

        inorder(root.left);
        System.out.print(" " + root.data);
        inorder(root.right);
    }

    public int summaryCount() {
        return summaryCount(root);
    }

    private int summaryCount(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.data.getCount() + summaryCount(node.left) + summaryCount(node.right);
    }

    public void preorder() {
        System.out.print("Pre-order:");
        preorder(root);
        System.out.println();
    }

    private void preorder(Node<T> root) {
        if (root == null) return;

        System.out.print(" " + root.data);
        preorder(root.left);
        preorder(root.right);
    }


    public void postorder() {
        System.out.print("Post-order:");
        postorder(root);
        System.out.println();
    }

    private void postorder(Node<T> root) {
        if (root == null) return;

        postorder(root.left);
        postorder(root.right);
        System.out.print(" " + root.data);
    }

    public static void main(String[] args) {
        CarTree<Car> myTree = new CarTree<Car>();

        myTree.inorder();
        myTree.preorder();
        myTree.postorder();
    }
}