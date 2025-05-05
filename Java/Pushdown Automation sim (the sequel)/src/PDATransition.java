public class PDATransition {
    //think of the read, pop, push part of transitions
    int start;
    int end;
    char read;
    String pop;
    String push;
    public PDATransition(int start, int end, String read, String pop, String push) {
        this.start = start;
        this.end = end;
        if(read.equals("LAMBDA")) {
            this.read = 0;
        } else {
            this.read = read.toCharArray()[0];
        }
        if(pop.equals("LAMBDA")) {
            this.pop = "";
        } else {
            this.pop = pop;
        }
        if(push.equals("LAMBDA")) {
            this.push = "";
        } else {
            this.push = push;
        }
    }
    public char getRead() {
        return read;
    }
    public int getStart() {
        return start;
    }
    public int getEnd() {
        return end;
    }
    public String getPop() {
        return pop;
    }
    public String getPush() {
        return push;
    }
    public void runTransition(PDAStack stack) throws PDAException {//runs a transition from the pop to the push.
        stack.pop(this.pop);
        stack.push(this.push);
    }
}
