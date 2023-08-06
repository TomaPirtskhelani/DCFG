package dk;

import java.util.*;

import grammar.Grammar;
import grammar.Production;
import grammar.Symbol;

public class DK1 {

    private final State start;
    private final HashSet<State> states;
    private final Grammar g;

    public DK1(Grammar g) {
        // Initialize the Grammar
        this.g = g;

        // Initialize the Start State
        start = new State();
        for (Production production : g.getProductions()) {
            if (production.getLeft().equals(g.getStart())) {
                start.addItem(new Item(production, 0, g.getTerminals()));
            }
        }

        // Make Epsilon Moves from the Start State
        start.makeEpsilonMoves(g);

        // Put the start state in the states
        states = new HashSet<>();
        states.add(start);

        // Make Transitions and Find all States
        Queue<State> queue = new LinkedList<>();
        HashSet<State> queueCheck = new HashSet<>();
        queue.add(start);
        queueCheck.add(start);

        while (!queue.isEmpty()) {
            State currentState = queue.remove();
            currentState.makeShiftMoves(states, g);

            System.out.println(queue.size() + " " + states.size());

            for (Map.Entry<Symbol, State> entry : currentState.getPaths().entrySet()) {
                State newState = entry.getValue();

                boolean flag = false;
                for (State state : queueCheck){
                    if (state.sameItems(newState)){
                        flag = true;
                        break;
                    }
                }
                if (flag) continue;

                queue.add(newState);
                queueCheck.add(newState);
            }
        }
    }

    // DK1 Test
    public boolean Test() {
        for (State state : states) {
            for (Item R1 : state.getItems()) {
                for (Item R2 : state.getItems()) {
                    if (!R1.isComplete()) break;
                    if (R1.equals(R2)) continue;
                    if (R2.isComplete()) {
                        for (Symbol symbol : R1.getLookaheads()) {
                            if (R2.getLookaheads().contains(symbol)) {
                                System.out.println("Rejecting State Condition_1: ");
                                System.out.println(R1);
                                System.out.println(R2);
                                System.out.println(state.toStringOnlyState());
                                return false;
                            }
                        }
                    } else {
                        Symbol currentSymbol = R2.currentSymbol();

                        if (R1.getLookaheads().contains(currentSymbol)) {
                            System.out.println("Rejecting State Condition_2: ");
                            System.out.println(R1);
                            System.out.println(R2);
                            System.out.println(state.toStringOnlyState());
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public void parseString(String validString) {

        String str = validString.replaceAll("\\s", "");

        ArrayList<Symbol> validStringArray = new ArrayList<>();
        int index = 0;
        while (index < str.length()) {
            Symbol currentSymbol = Symbol.firstSymbolInString(str.substring(index), g);
            validStringArray.add(currentSymbol);
            assert currentSymbol != null;
            index += currentSymbol.length();
        }

        Item handle;

        while (!validStringArray.get(0).equals(g.getStart())) {
            handle = findHandle(validStringArray);
            System.out.println(validStringArray + "     [handle: " + handle +"]");
            validStringArray = makeReduction(validStringArray, handle.getProduction(), handle.getDotIndex());
        }

        System.out.println(validStringArray);
    }

    public Item findHandle(ArrayList<Symbol> validStringArray) {

        State currentState = start;
        int dotIndex = 0;
        Symbol currentSymbol = validStringArray.get(dotIndex);

        while (currentState != null) {

            // If complete Item Check if it is the handle
            if (!currentState.getCompleteItems().isEmpty()) {
                for (Item item : currentState.getCompleteItems()) {
                    Symbol lookahead = validStringArray.size() > dotIndex ? validStringArray.get(dotIndex) : null;

                    if (lookahead != null && !item.getLookaheads().contains(lookahead)) continue;
                    return new Item(item.getProduction(), dotIndex, new HashSet<>());
                }
            }

            // Make transition
            boolean madeTransition = false;
            for (Map.Entry<Symbol, State> entry : currentState.getPaths().entrySet()) {
                Symbol transitionSymbol = entry.getKey();
                State transitionState = entry.getValue();

                if (transitionSymbol.equals(currentSymbol)) {
                    dotIndex++;
                    currentState = transitionState;
                    currentSymbol = validStringArray.size() > dotIndex ? validStringArray.get(dotIndex) : null;
                    madeTransition = true;
                    break;
                }
            }

            if (!madeTransition) {
                return null;
            }
        }

        return null;
    }

    public ArrayList<Symbol> makeReduction(ArrayList<Symbol> validStringArray, Production handle, int dotIndex) {

        int experimentIndex = dotIndex - handle.getRight().size();

        int handleIndex = -1;
        for (int i = 0; i <= validStringArray.size() - handle.getRight().size(); i++){
            boolean equals = true;
            for(int j = 0; j < handle.getRight().size(); j++) {
                if (!validStringArray.get(i + j).equals(handle.getRight().get(j))){
                    equals = false;
                    break;
                }
            }
            if (equals) {
                handleIndex = i;
                break;
            }
        }

        System.out.println(handleIndex + " vs " + experimentIndex);
        handleIndex = experimentIndex;

        // If find Handle, make a reduction
        if(handleIndex != -1) {
            ArrayList<Symbol> newValidStringArray = new ArrayList<>();

            for (int i = 0; i < handleIndex; i++) {
                newValidStringArray.add(validStringArray.get(i));
            }
            newValidStringArray.add(handle.getLeft());
            for (int i = handleIndex + handle.getRight().size(); i < validStringArray.size(); i++) {
                newValidStringArray.add(validStringArray.get(i));
            }

            return newValidStringArray;
        }

        return null;
    }

    public State getStart() {
        return start;
    }

    public HashSet<State> getStates() {
        return states;
    }

    public Grammar getG() {
        return g;
    }

}
