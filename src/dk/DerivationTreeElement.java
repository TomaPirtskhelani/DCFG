package dk;

import grammar.Symbol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DerivationTreeElement {

    private final Symbol label;
    private DerivationTreeElement father;
    private DerivationTreeElement firstSon;
    private DerivationTreeElement brother;

    public DerivationTreeElement(Symbol label) {
        this.label = label;
    }

    public static ArrayList<DerivationTreeElement> updateTheParseTree(ArrayList<DerivationTreeElement> parseTree, Item handle) {

        Symbol parentSymbol = handle.getProduction().getLeft();
        int rightIndex = handle.getDotIndex();
        int leftIndex = rightIndex - handle.getProduction().getRight().size();

        // Make Brothers
        for (int i = leftIndex; i < rightIndex - 1; i++) {
            parseTree.get(i).setBrother(parseTree.get(i + 1));
        }

        // Create father and make connection
        DerivationTreeElement father = new DerivationTreeElement(parentSymbol);
        father.setFirstSon(parseTree.get(leftIndex));
        for (int i = leftIndex; i < rightIndex; i++) {
            parseTree.get(i).setFather(father);
        }

        // Erase Children from the parse tree array and put father in their place
        ArrayList<DerivationTreeElement> newParseTree = new ArrayList<>();

        for (int i = 0; i < leftIndex; i++) {
            newParseTree.add(parseTree.get(i));
        }
        newParseTree.add(father);
        for (int i = rightIndex; i < parseTree.size(); i++) {
            newParseTree.add(parseTree.get(i));
        }

        /*
        System.out.println("Print the father creation!");
        System.out.println("The Handle: " + handle.getProduction() + " DotID: " + handle.getDotIndex());
        System.out.println(father.getLabel());
        DerivationTreeElement son = father.getFirstSon();
        while (son != null) {
            System.out.print(son.getLabel() + ", ");
            son = son.getBrother();
        }
        System.out.println();
        */
        return newParseTree;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        Queue<DerivationTreeElement> queue = new LinkedList<>();
        int currentHeightElementsNumber = 1;
        int nextHeightElementsNumber = 0;

        queue.add(this);
        while (!queue.isEmpty()){

            DerivationTreeElement currentElement = queue.remove();

            sb.append(currentElement.getLabel()).append(", ");
            currentHeightElementsNumber--;

            DerivationTreeElement child = currentElement.firstSon;
            while (child != null){
                queue.add(child);
                nextHeightElementsNumber++;
                child = child.getBrother();
            }

            if (currentHeightElementsNumber == 0) {
                sb.append("\n");
                currentHeightElementsNumber = nextHeightElementsNumber;
                nextHeightElementsNumber = 0;
            }

        }

        return sb.toString();

    }

    public Symbol getLabel() {
        return label;
    }

    public DerivationTreeElement getFather() {
        return father;
    }

    private void setFather(DerivationTreeElement father) {
        this.father = father;
    }

    public DerivationTreeElement getFirstSon() {
        return firstSon;
    }

    private void setFirstSon(DerivationTreeElement firstSon) {
        this.firstSon = firstSon;
    }

    public DerivationTreeElement getBrother() {
        return brother;
    }

    private void setBrother(DerivationTreeElement brother) {
        this.brother = brother;
    }

}
