public class SortedTree<E extends Comparable<E>> {
    public void insert(E element) {
        //...
    }

    public static void main(String[] args) {
        SortedTree<String> sortedTreeOfStrings = new SortedTree<String>();
        sortedTreeOfStrings.insert("xyz");
        sortedTreeOfStrings.insert("abc");
        sortedTreeOfStrings.insert("klm");
    }
}
