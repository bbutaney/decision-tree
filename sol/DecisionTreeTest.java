package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.List;
public class DecisionTreeTest {
    // Constructor for DecisionTreeTest tester class - don't need to add anything in here!
    public DecisionTreeTest () {}

    /* ---- Tests to turn in ---- */
    // Unit Tests for getDecision
//    @Test
//    public void unitTests() {
//        DecisionTreeCSVParser s = new DecisionTreeCSVParser();
//        List<Row> rows1 = new ArrayList<Row>();
//        List<Row> smallDataset = s.parse("/Users/School/Desktop/Byron_And_Kaleb_Practice_CSV.csv");
//        for (int i = 0; i < 10; i++) {
//            rows1.add(smallDataset.get(i));
//        }
//        Dataset ds = new Dataset(new ArrayList<String>(), rows1);
//
//        TreeGenerator tt = new TreeGenerator();
//
//        tt.generateTree(ds, "isPoisonous");
//
//        Row r1 = ds.rows.get(0); // first row edge case
//        Row r2 = ds.rows.get(4); // general case
//        Row r3 = ds.rows.get(8); // general case
//        Row r4 = ds.rows.get(ds.rows.size() - 1); // last row edge case
//        assertEquals(tt.getDecision(r1), "True");
//        assertEquals(tt.getDecision(r2), "False");
//        assertEquals(tt.getDecision(r3), "True");
//        assertEquals(tt.getDecision(r4), "True");
//
//    }

//    @Test (expected = RuntimeException.class)
//    public void emptyDatasetTest() {
//        DecisionTreeCSVParser s = new DecisionTreeCSVParser();
//        List<Row> rows1 = new ArrayList<Row>();
//        List<Row> emptyDataset = s.parse("/Users/School/Desktop/Empty_Practice_CSV.csv");
//        s.generateTree(emptyDataset);
//    }

    @Test
    public void basicFunctionalityTests() {
        // Leaf getDecision test
        Leaf testLeaf = new Leaf("decision :)");
        Row testRow = new Row("test row");
        assertEquals(testLeaf.getDecision(testRow), "decision :)");
        // Node getDecision test
        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        ArrayList<Edge> edgeList2 = new ArrayList<Edge>();
        Leaf leaf = new Leaf("bottom of tree!");
        edgeList2.add(new Edge("value2", leaf));
        Node node2 = new Node("attributeName2", "defaultDecision2", edgeList2);
        Leaf notLeaf = new Leaf("no no no!");
        edgeList.add(new Edge("notValue", notLeaf));
        edgeList.add(new Edge("value", node2));
        Node node = new Node("attributeName", "defaultDecision", edgeList);
        Row row = new Row("row");
        row.setAttributeValue("attributeName", "value");
        row.setAttributeValue("attributeName2", "value2");
        assertEquals(node.getDecision(row), "bottom of tree!");
    }
}