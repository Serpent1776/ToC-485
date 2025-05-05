import java.util.ArrayList;
public class PushDownAutomation {
    protected class State {//the states of the automata
        boolean inital;
        boolean isFinal;
        ArrayList<PDATransition> transitions; //transitions to adjacent states
        ArrayList<State> adjacentStates; //adjacent states that it can go to
        int[] adjacents; //adjacent find help
        int position;

        protected State(boolean initial, boolean isFinal, int[] adjacents, int position) {
            this.inital = initial;
            this.isFinal = isFinal;
            this.transitions = new ArrayList<PDATransition>();
            this.adjacentStates = new ArrayList<State>();
            this.adjacents = adjacents;
            this.position = position;
            
        }
        protected boolean isadjacent(int target) {//checks if a state to a given target state, given as int.
            boolean isadjacent = false;
            for(int i = 0; !isadjacent && i < adjacents.length; i++) {
                if(adjacents[i] == target) {isadjacent = true;}
            }
            return isadjacent;
        }
        protected boolean isadjacent(State target) { //overloaded this because why not.
            boolean isadjacent = false;
            for(int i = 0; !isadjacent && i < adjacents.length; i++) {
                if(adjacents[i] == target.position) {isadjacent = true;}
            }
            return isadjacent;
        }

        
        
    }
    //classic linked list with states
    int size; //maximum number of states
    char[] language; //the signa of chars allowed to be in said automation
    State[] states; //array of states
    State start; //starting state
    public PushDownAutomation(int size, char[] language) {//constructor that makes an empty automata. 
        //use addState to initalize start
        this.size = size;
        this.states = new State[size];
        this.language = language;
    }
    public void addState(String[] stateStr) throws PDAException{//for adding a state
        if(start == null) {//empty start case
            if(stateStr[0].equals("INITAL")) {//add a starting state or else
                try {
                    int[] adj = new int[stateStr.length-2]; //adajacent states
                for(int i = 2; i < stateStr.length; i++) {
                    adj[i-2] = Integer.parseInt(stateStr[i]);
                }
                start = new State(true, false, adj, Integer.parseInt(stateStr[1]));
                this.states[start.position] = start;
                if(start.isadjacent(start)) { //if check to allow loops on the start
                    start.adjacentStates.add(start);
                }
                if(start.adjacents.length < 2) {
                    throw new PDAException();
                }
            } catch (Exception e) {
                throw new PDAException("Error: inital states must have adjacent states.");    
            }
            } else {
                throw new PDAException("Error: please provide an INITAL State.");
            }
        } else { //makes new state with the possibilty of it being final
            if(stateStr[0].equals("INTIAL")) { //No more intial states can exist!
                throw new PDAException("Error: there can be one inital state.");
            }
            if(stateStr[0].equals("FINAL")) { //every state can be a final state!
                int[] adj = new int[stateStr.length-2];
                for(int i = 2; i < stateStr.length; i++) {
                    adj[i-2] = Integer.parseInt(stateStr[i]);
                }        
                State newState = new State(false, true, adj, Integer.parseInt(stateStr[1]));
                this.states[newState.position] = newState; 
                for(State s: this.states) { //add state to adjacent states loop
                    if(s != null && s.isadjacent(newState)) {
                        s.adjacentStates.add(newState);
                    }
                }
                for(int i: adj) { //add adjacent states to state loop
                if(this.states[i] != null) {
                newState.adjacentStates.add(this.states[i]);
                }
                }
                //System.out.println(start.adjacents[0]); debugging
            } else { //if the state isn't a final state, it can still be added
                int[] adj = new int[stateStr.length-1]; //values tweaked here
                for(int i = 1; i < stateStr.length; i++) {
                    adj[i-1] = Integer.parseInt(stateStr[i]);
                }        
                State newState = new State(false, false, adj, Integer.parseInt(stateStr[0]));
                this.states[newState.position] = newState; 
                for(State s: this.states) { //add state to adjacent states loop
                    if(s != null && s.isadjacent(newState)) {
                        s.adjacentStates.add(newState);
                    }
                }
                for(int i: adj) { //add adjacent states to state loop
                    if(this.states[i] != null) {
                        newState.adjacentStates.add(this.states[i]);
                    }
                }
            }
        }
    }   

    public void addTransition(String[] transStr) throws PDAException {//adds the transitions between states, info selected by a string
        if(this.start == null) {throw new PDAException("Error: have an INITAL state please.");} //inital check
        if(transStr[3].length() == 1 || !(transStr[3].equals("LAMBDA"))) { //appropiate read string check
            if(transStr[3].length() == 1) { //goes into a lang check when it's obviously a char
                for(int i = 0; i < language.length; i++) {
                    if(transStr[3].equals(language[i] + "")) {break;} else if(i == language.length-1) {throw new PDAException("Error: read char outside language");}
                }
            }
            if(transStr[3].length() > 1) {
            throw new PDAException("Error: please have an appropiate read string");
            }
        }
        try {//check then add
        if(this.states[Integer.parseInt(transStr[1])].isadjacent(Integer.parseInt(transStr[2]))) {  
        PDATransition transition = new PDATransition(Integer.parseInt(transStr[1]), Integer.parseInt(transStr[2]), transStr[3], transStr[4], transStr[5]);
        this.states[Integer.parseInt(transStr[1])].transitions.add(transition); //uses the start to link to the starting State
        //note: duplication doesn't matter
        } else {
            throw new PDAException();
        }
        } catch (Exception e) {//no addition when the state is not found. 
            throw new PDAException("Error: Target state was not found!");
        }
    }
    public boolean test(String str, PDAStack stack) throws PDAException {//the big test of your response to the program
        //runs through all of states unless the automation doesn't have a final state.
        for(int i = 0; i < states.length; i++) {
            if(states[i].isFinal) {break;} else if (i == states.length - 1){
                throw new PDAException("Error: have a FINAL state please."); 
            }
        } 
        //A thrown exception is a signal of failure!
        boolean withinLanguage = false;
        boolean withinTransitions = false; //meant to check if the readable is inside the transitions.
        int subStart = 0; //The start of the substring. +1 and it's the end of it, usually. This is also used in end check.
        State navigator = start; //The ship across the automation ocean. The final state is its final stop. 
        int transitional = 0; //The transition helper. helps for lookup of transition. 
        if(str.equals("LAMBDA") || str.equals("")) { //LAMBDA case
            transitional = 0;
            char s = 0; //null readable is insanely helpful for this
            while(!navigator.isFinal) {
                for(int i = 0; i < navigator.transitions.size(); i++) {//check
                    if(navigator.transitions.get(i).read == s) {
                        withinTransitions = true;
                        transitional = i;
                        break;
                    }
                }
                if(!withinTransitions) {
                    break;
                } else {//optimial transition finding
                    for(int i = 0; i < navigator.transitions.size(); i++) {
                        if(navigator.transitions.get(i).read == s && stack.stackEqual(navigator.transitions.get(i).pop)) {
                            withinTransitions = true;
                            transitional = i;
                            break;
                        }
                }
                }
                //the ship now goes
                navigator.transitions.get(transitional).runTransition(stack);
                transitional = navigator.transitions.get(transitional).getEnd();
                navigator = states[transitional];
            }
            return stack.isEmpty(); //the only check under the LAMBDA case
        }
        while(!navigator.isFinal) { //other string case
            if(subStart == str.length() || navigator == null) {//the usual end case
                withinTransitions = false;
                if(navigator != null) {
                    transitional = 0;
                    char s = 0; //null readable basically.
                    while(!navigator.isFinal) {
                        for(int i = 0; i < navigator.transitions.size(); i++) {//lambda transition check
                            if(navigator.transitions.get(i).read == s) {
                                withinTransitions = true;
                                transitional = i;
                                break;
                            }
                        }
                        if(!withinTransitions) {
                            break;
                        } else {//optimial transition finding
                            for(int i = 0; i < navigator.transitions.size(); i++) {
                                if(navigator.transitions.get(i).read == s && stack.stackEqual(navigator.transitions.get(i).pop)) {
                                    withinTransitions = true;
                                    transitional = i;
                                    break;
                                }
                        }
                        }
                    //the ship now goes
                    navigator.transitions.get(transitional).runTransition(stack);
                    transitional = navigator.transitions.get(transitional).getEnd();
                    navigator = states[transitional];
                }
                break; //to exit the testing loop with the ship parked!
                }
            }
            withinLanguage = false;
            withinTransitions = false;
            transitional = 0; //the refresh
            char readable = str.charAt(subStart); //the read string!
            for(int i = 0; i < this.language.length; i++) { //lang check
                if(this.language[i] == readable) {
                    withinLanguage = true;
                }
            }
            if(!withinLanguage) {
                break; //exit on failure
            }
            for(int i = 0; i < navigator.transitions.size(); i++) {//transition check
                if(navigator.transitions.get(i).read == readable) {
                    withinTransitions = true;
                    transitional = i;
                }
            }
            if(!withinTransitions) { //end case on failure to help find a friendly state.
                char s = 0;
                    for(int i = 0; i < navigator.transitions.size(); i++) {
                        if(navigator.transitions.get(i).read == s) {
                            withinTransitions = true;
                            transitional = i;
                            subStart--;
                            break;
                        }
                    }
                 readable = s;   
            } else { //success case is then to find the most optimal state
                for(int i = 0; i < navigator.transitions.size(); i++) {
                    if(stack.stackEqual(navigator.transitions.get(i).pop) && navigator.transitions.get(i).read == readable) {
                        transitional = i;
                        break;
                    }    
                }
            }
            if(this.states[navigator.transitions.get(transitional).end].isFinal) {//meant to help your string not fail with "NO"
                for(int i = transitional + 1; i < navigator.transitions.size(); i++) {
                    if(stack.stackEqual(navigator.transitions.get(i).pop) && navigator.transitions.get(i).read == readable) {
                        transitional = i;
                        break;
                    }    
                }
            }
            //the ship now can go
            navigator.transitions.get(transitional).runTransition(stack); //transition enacted on stack
            transitional = navigator.transitions.get(transitional).getEnd(); //transition meant to represent the ship's destignation
            navigator = states[transitional]; //the ship finally goes proper
            subStart++; //the substring is advanced.
        }
        return stack.isEmpty() && str.substring(subStart).equals(""); //check case at final state to see if you pass!
    }
}
