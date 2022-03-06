package sol;

import src.DecisionTreeCSVParser;
import src.IDataset;
import src.Row;
import java.util.List;
import java.util.ArrayList;

/**
 * A class that implements the IDataset interface,
 * representing a training data set.
 */
public class Dataset implements IDataset {
   private List<Row> rows;
   private List<String> attributeList;

    public Dataset (List<String> attributeList, List<Row> rows) {
        this.rows = rows;
        this.attributeList = attributeList;
    }

    /*
     * Gets list of attributes in the dataset
     *
     * @return a list of strings
     */
    public ArrayList<String> getAttributeList() {
        ArrayList<String> l = new ArrayList<String>();
        for (String s : this.rows.get(0).getAttributes()) {
            l.add(s);
        }
        return l;
    };

    /*
     * Gets list of data objects (row) in the dataset
     *
     * @return a list of Rows
     */
    public List<Row> getDataObjects() {
        return this.rows;
    };

    /*
     * finds the size of the dataset (number of rows)
     *
     * @return the number of rows in the dataset
     */
    public int size() {
        return this.rows.size();
    };


    /*
     * Adds a row to the dataset
     *
     * @return void, adds a row
     */
    public void addRow (Row r) {
        this.rows.add(r);
    }


    /*
     * This partitions the dataset, it returns all of the
     * rows in a dataset that share the same value under a specific
     * attributeName
     *
     * @param trainData the dataset
     *        attName  an attributeName/category
     *        attVal   an attributeValue
     *
     *
     * @return a partitioned dataset
     */


    public Dataset
    getRemainingData(Dataset trainData, String attName, String attVal) {
        Dataset temp =
                new Dataset(trainData.attributeList, new ArrayList<Row>());
        for (Row r: trainData.rows) {
            if (r.getAttributeValue(attName).equals(attVal)){
                temp.addRow(r);
                temp.attributeList.remove(attName);
            }
        }
        return temp;
    }
    /*
     * Returns the rows
     *
     * @return List of rows, returns rows
     */
    public List<Row> getRows(){
        return this.rows;
    }

    /*
     * Checks if the attrbute list contains a string
     *
     * @return boolean, does attributeList contain
     * the string
     */
    public boolean isInAttList(String s){
       return this.attributeList.contains(s);
    }

    /*
     * Initializes the dataset in the generator
     *
     * @return void, sets the dataset
     */
    public void initAttList(List<String> l){
        this.attributeList = l;
    }

    /*
     * Returns the size of attributeList
     *
     * @return int, size of attributeList
     */
    public int attListSize(){
       return this.attributeList.size();
    }
}
