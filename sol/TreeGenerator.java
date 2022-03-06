package sol;

import src.ITreeGenerator;
import src.Row;


import java.util.List;
import java.util.Random;
import java.util.ArrayList;


/**
 * A class that implements the ITreeGenerator interface
 * used to generate a tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {
    Random random = new Random();
    ITreeNode topNode = new Node("", "", null);


    public TreeGenerator(){}

    /**
     * Finds and returns all instances in an ArrayList of
     * strings that are equal to a string
     *
     * @param alos1, an ArrayList of strings
     *        value a string
     *
     * @return alos2, an arraylist of all the instances
     */
    private ArrayList<String>
    addAllInstancesOf(ArrayList<String> alos1, String value){
        ArrayList<String> alos2 = new ArrayList<>();

        for (String s : alos1) {
            if (s.equals(value)) {
                alos2.add(s);
            }
        }
        return alos2;
    }


    /**
     * Finds and returns all instances in an ArrayList of
     * strings that are not equal to a string
     *
     * @param alos1 an ArrayList of strings
     *        value a string
     *
     * @return alos2 an arraylist of all the instances
     */
    private ArrayList<String>
    removeAllInstancesOf(ArrayList<String> alos1, String value) {
        ArrayList<String> alos2 = new ArrayList<>();

        for (String s : alos1) {
            if (!s.equals(value)) {
                alos2.add(s);
            }
        }
        return alos2;

    }

    /**
     * Using the previous two methods, sorts the list
     * into a list of lists that only have the same value
     *
     * @param a an ArrayList of strings
     *
     *
     * @return a sorted list of lists, each list hav the same value
     */
    private ArrayList<ArrayList<String>> sortList(ArrayList<String> a){
        ArrayList<ArrayList<String>> alos2 = new ArrayList<>();
        for(String s: a){
            alos2.add(addAllInstancesOf(a, s));
            a = removeAllInstancesOf(a, s);
        }
        return alos2;
    }

    /**
     * Convert sorted list to an array of integers
     *
     * @param alol an ArrayList of arraylists of strings
     *
     *
     * @return an array of numbers
     * representing the length of each list in the list of lists
     */
    private ArrayList<Integer>
    getLengthList(ArrayList<ArrayList<String>> alol) {
        ArrayList<Integer> temp = new ArrayList<>();
        for(ArrayList<String> a : alol) {
            temp.add(a.size());
        }
        return temp;
    }

    /**
     * Gets the max value out of the list of integers
     *
     * @param aloi an ArrayList of arraylists of integers
     *        max, init value
     *
     *
     * @return  the maximum of the integers in the list of integers
     */
    private int maxLength(ArrayList<Integer> aloi, int max) {
        for (Integer i: aloi) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    /**
     * Takes in an integer (the max), and matches it to the length
     * in the sorted list of lists. Returns the first element of the
     * matched list
     *
     * @param aloa an ArrayList of arraylists of strings
     *         i the max value
     *
     *
     * @return a string, representing the first element of the matched list
     */
    private String bestString(int i, ArrayList<ArrayList<String>> aloa) {
        for (ArrayList<String> a : aloa) {
            if (a.size() == i) {
                return a.get(0);
            }
        }
        return "";
    }

    /**
     * Returns the most common decision in a given dataset
     *
     * @param ds a dataset
     *         targetAttribute
     *
     *
     * @return a the most common value under a specific
     * attribute (the target attribute)
     */
    private String getMostCommonDecision(Dataset ds, String targetAttribute) {

        ArrayList<String> strings = new ArrayList<>();
        for (Row r : ds.getRows()) {
            strings.add(r.getAttributeValue(targetAttribute));
        }
        return bestString(maxLength(
                getLengthList(sortList(strings)), 0)
                , sortList(strings));

    }

    /**
     * indicates whether all the rows in the inputted row list have the
     * same target attribute value
     * @param rows - the list of rows to check
     * @param targetAttribute - the attribute to check each row in rows against
     * @return a boolean indicating
     * whether all the rows in the inputted row list have the
     * same target attribute value
     */

    private boolean endRecursion(List<Row> rows, String targetAttribute){
        String s = rows.get(0).getAttributeValue(targetAttribute);
        for (Row r : rows){
            if(!r.getAttributeValue((targetAttribute)).equals(s)){
                return false;
            }
        }
        return true;
    }

    /**
     * picks a random attribute from training data to split on
     * @param trainingData - the dataset to pick an attribute from

     * @return the attribute name to split on next
     */
    private String pickNewAtt(Dataset trainingData) {
        List<String> attCopy = trainingData.getAttributeList();
        int index = random.nextInt(attCopy.size());
        if (!trainingData.isInAttList(attCopy.get(index))) {

            return pickNewAtt(trainingData);

        }
        return attCopy.get(index);
    }

    // TODO: Implement methods declared in ITreeGenerator interface!
    /**
     * Generates the tree for a given training dataset.
     *
     * @param trainingData    the dataset to train on
     * @param targetAttribute the attribute to predict
     */

    private ITreeNode
    generateTreeEngine(Dataset trainingData, String targetAttribute) {

        // initialize training data attribute list
        trainingData.initAttList(trainingData.getAttributeList());

        /* check if we need to end recursion through tree
         * if so, this is the end of the tree, so create a leaf for the decision
         */

        if(trainingData.size() == 0){
            throw new RuntimeException("Dataset is empty!");
        }
        if (trainingData.getRows().size() == 1
                || trainingData.attListSize() == 0
                || this.endRecursion(trainingData.getRows(), targetAttribute)){
            return new Leaf(trainingData.getRows()
                    .get(0).getAttributeValue(targetAttribute));
        }
        else {
            // choose an attribute to split on
            String splitAttribute = pickNewAtt(trainingData);

            /* Create an edge for each value of that attribute in the
             * dataset partioned for all splitAttribute values */
            ArrayList<String> attVals = new ArrayList<>();
            for (Row r : trainingData.getRows()) {
                String value = r.getAttributeValue(splitAttribute);
                if (!attVals.contains(value)) {
                    attVals.add(value);
                }
            }

            ArrayList<Edge> extendingEdges = new ArrayList<>();
            for (String s : attVals) {

                Dataset copy = trainingData.getRemainingData(trainingData, splitAttribute, s);

                extendingEdges.add(new Edge(s,
                        this.generateTreeEngine(copy, targetAttribute)));
            }

        /* Set the top node with the attribute we split on and set
        its default value */
            this.topNode = new Node(splitAttribute,
                    this.getMostCommonDecision(trainingData, targetAttribute)
                    , extendingEdges);
            return this.topNode;
        }
    }

    public void generateTree(Dataset trainingData, String targetAttribute) {
        this.topNode = generateTreeEngine(trainingData, targetAttribute);
    }

    /**
     * Looks up the decision for a datum in the decision tree.
     *
     * @param datum the datum to lookup a decision for
     * @return the decision of the row
     */
    public String getDecision(Row datum) {
        return this.topNode.getDecision(datum);
    }


}



