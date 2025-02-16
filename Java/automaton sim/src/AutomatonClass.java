import java.util.Scanner;

public class AutomatonClass{
    protected class Node {
        private int stateId;
        private int[] transitionLocations; //other state ids that it is next to
        private Node[] nexts; //
        private Node next;
        private String[] transitionStrings; //this is for the strings on the arrows
        private boolean finalState;
        private boolean trapState;

        protected Node(int stateId, int[] transitionLocations, Node[] nexts, String[] transitionStrings, boolean finalState, boolean trapState) throws AutomatonException {
            this.stateId = stateId;
            this.transitionLocations = transitionLocations;
            this.nexts = nexts;
            this.next = null;
            this.transitionStrings = transitionStrings;
            this.finalState = finalState;
            this.trapState = trapState;
        }
        protected void next(Node next) {
            this.next = next;
        }
    }
    private int len; //states limit of automation
    private int alphabetLen; //used to limit the arrows, knowing that this project's scope is DFA exclusive
    private String[] alphabet; //accounts for the error.
    private int[][] transitions; //extra storage to link the states together, might be uncessnessary. 
    
    AutomatonClass(Scanner txtFile) throws Exception {
        //will have formatting errors from the file alone.  

    }

    public void linkUpStates() throws AutomatonException {
        //will have ban cases if it is broken by the simulation being an NFA such will have exceptions

    }
    public void simulate(String test) throws AutomatonException { 
        //simulates a string through the automation, may be unescessary depnding on implication in the driver
        //accounts for the error.  
    }
}
