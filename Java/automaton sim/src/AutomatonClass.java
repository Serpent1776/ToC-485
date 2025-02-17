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
    private char[] alphabet; //accounts for fromatting errors
    private int[][] transitions; //extra storage to link the states together, might be uncessnessary. 
    
    AutomatonClass(Scanner txtFile) throws Exception {
        
        //will have formatting errors from the file alone.  
        this.len = txtFile.nextInt();
        System.out.print(txtFile.nextLine()); //debugging
        this.alphabetLen = txtFile.nextInt();
        System.out.print(txtFile.nextLine()); //debugging
        this.alphabet = txtFile.nextLine().toCharArray(); //takes string and turns it into an char array of String x.length(). 
       this.transitions = new int[alphabetLen][len];
       int countr = 0; //front!
       for(int i = 0; (txtFile.nextLine().equals("")) ;i++) { //state storage loop, I hope I don't need to explain this
       Integer a = new Integer(txtFile.nextInt()); //the integer storage glitch will be debugged inevitably.
       countr++;
        this.transitions[i%(alphabetLen+1)][countr] = a.intValue(); //O(log n)?
       }
    }

    public void linkUpStates() throws AutomatonException {
        //will have ban cases if it is broken by the simulation being an NFA such will have exceptions
        //the linked stuff will have what's in the node class


    }
    public void simulate(String test) throws AutomatonException { 
        //simulates a string through the automation, may be unescessary depnding on implication from the driver
        //accounts for the error in the project prompt.  
        
    }
}
