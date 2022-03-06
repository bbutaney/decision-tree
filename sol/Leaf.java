package sol;

import src.Row;


/**
 * A class that implements the interface of ITreeNode
 * this represents a leaf, which is the bottom/decision node of a tree
 */
public class Leaf implements ITreeNode{
    private String decision;

    public Leaf (String decision){
        this.decision = decision;
    }

    /*
     * Gets a decision from a leaf
     *
     * @return a String that represents a decision
     */
    public String getDecision (Row forDatum) {
        return this.decision;
    }
}
