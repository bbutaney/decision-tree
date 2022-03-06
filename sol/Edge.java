package sol;


/*
 * A class represents an edge, this is a class that connects two ITreeNode
 */
public class Edge {
   public String value;
   public ITreeNode next;

    public Edge (String value, ITreeNode next) {
        this.value = value;
        this.next = next;

    }
}
