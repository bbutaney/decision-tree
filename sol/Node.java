//package sol;
//import src.Row;
//
//import java.util.ArrayList;
//
//public class Node implements ITreeNode{
//    String attributeName;
//    String defaultDecision;
//    ArrayList<Edge> edges;
//
//    public Node(String attributeName,
//                String defaultDecision, ArrayList<Edge> edges) {
//        this.attributeName = attributeName;
//        //this.attributeValue = attributeValue;
//        this.defaultDecision = defaultDecision;
//        this.edges = edges;
//    }
//
//    /**
//     * Recursively traverses decision tree to return tree's decision for a row.
//     *
//     * @param forDatum the datum to lookup a decision for
//     * @return the decision tree's decision
//     */
//    public String getDecision(Row forDatum) {
//        /*
//        1. find which attribute of row to look at
//        2. compare to all edges that extend out of this node
//        3. edge.next.getDecision(forDatum); which will be recursive
//        4. add base case for when the recursion reaches a leaf;
//         */
//        String forDatumAttribute = "";
//        // find the attribute in forDatum that corresponds to this node
//        for(String a: forDatum.getAttributes()) {
//            if(a.equals(this.attributeName)) {
//                forDatumAttribute = a;
//            }
//        }
//
//        Edge nextEdge = new Edge("", null);
//
//        // searches edges for attribute value. when found, recurs down that path
//        for(Edge e : edges){
//            if (forDatum.getAttributeValue(forDatumAttribute).equals(e.value)) {
//                nextEdge = e;
//            }
//        }
//
//        // if none of the edges have the attribute value we're looking for, default decision
//        if(nextEdge.next == null) {
//            return defaultDecision;
//        }
//
//        return nextEdge.next.getDecision(forDatum);
//    }
//
//    public Edge getFromEdges(int i){
//        return edges.get(i);
//    }
//
//    public void xTend(Leaf next){
//        for(Edge e: this.edges){
//            if(e.next == null){
//                e.next = next;
//            } else {
//                e.next.xTend(next);
//            }
//        }
//    }
//
//    public void xTendTwo(Node next){
//        if(this.edges == null){
//            this.attributeName = next.attributeName;
//            this.defaultDecision = next.defaultDecision;
//            this.edges = next.edges;
//            for(Edge e: this.edges){
//
//            }
//        } else{
//        for(Edge e: this.edges){
//            if(e.next == null){
//                e.next = next;
//            } else {
//                e.next.xTendTwo(next);
//            }
//          }
//        }
//
//    }
//
//}

package sol;
import src.Row;

import java.util.ArrayList;

public class Node implements ITreeNode{
    private String attributeName;
    private String defaultDecision;
    private ArrayList<Edge> edges;

    public Node(String attributeName,
                String defaultDecision, ArrayList<Edge> edges) {
        this.attributeName = attributeName;

        this.defaultDecision = defaultDecision;
        this.edges = edges;
    }

    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision tree's decision
     */
    public String getDecision (Row forDatum) {
        String forDatumAttribute = "";

        // find the attribute in forDatum that corresponds to this node
        for (String a: forDatum.getAttributes()) {
            if (a.equals(this.attributeName)) {
                forDatumAttribute = a;
            }
        }
        Edge nextEdge = new Edge ("", null);

        // search edges for attribute value. when found, recur down that path
        for (Edge e : this.edges) {
            if (forDatum.getAttributeValue(forDatumAttribute).equals(e.value)) {
                nextEdge = e;
            }
        }

        /* if none of the edges have the attribute value
         we're looking for, default decision */
        if (nextEdge.next == null) {
            return defaultDecision;
        }

        return nextEdge.next.getDecision (forDatum);
    }
}

