
public class PDAStack { //basically an arraylist of char with stack-like features.
   char[] stack;
   int size;
   boolean empty;
   PDAStack() {
        this.stack = new char[50];

        this.size = 1; 
        this.stack[0] = 'Z';
        this.empty = false;
   }
   public void push(String push) { //pushes in the stack, not language dependent.
      
      if(!push.equals("")) {
         if(this.empty) {//empty case
            for(int i = 0; i < push.length();i++) {
               this.stack[i] = push.charAt(i);
            }
            this.empty = false;
         }  else if(size+1 < this.stack.length) {//other case under stack length
         char[] newStack = new char[this.stack.length];
         for(int i = 0; i < push.length();i++) {
            newStack[i] = push.charAt(i);
         }
         for(int i = 0; i < size; i++) {
            newStack[push.length()+i] = this.stack[i];
         }
         this.stack = newStack;
      } else {//above stack length case
         char[] newStack = new char[this.stack.length + 50];
         for(int i = 0; i < push.length();i++) {
            newStack[i] = push.charAt(i);
         }
         for(int i = 0; i < size; i++) {
            newStack[push.length()+i] = this.stack[i];
         }
         this.stack = newStack;
      }
      size+= push.length();
   }
   }
   public void pop(String pop) throws PDAException { //pops from the stack, very stack dependent!
      if(this.empty) {
         throw new PDAException("Error: Cannot pop from an empty stack!!!");
      }
      if(pop.equals("")) {/*pops nothing here*/} else if (stackEqual(pop)) {
         char[] newStack = new char[this.stack.length];
         if(size != 1) {
               for(int i = pop.length(); i < size; i++) {
                  newStack[i-pop.length()] = this.stack[i];
               }
               this.stack = newStack;
            } else { 
               newStack[0] = 0;
               this.stack = newStack;
               this.empty = true;
            }
            size-= pop.length();
            if(size == 0 && this.toString().equals("")) {
               this.empty = true;
            }
          } else {//throw when the pop doesn't equal what the stack has. Helps to kick bad strings away
            throw new PDAException("Wrong String Error: Nothing was popped!");
         }
   }
   public boolean isEmpty() {
       return empty;
   }
   public boolean stackEqual(String popstr) { //makes sure what is popped with from the top of the stack.
      //also used to make advantageous moves during testing
      String e = "";
      for(int i = 0; i < popstr.length(); i++) {
         e += stack[i];
      }
      return popstr.equals(e);
      
   }
   public char getTop() { //outdated, but present
       return this.stack[0];
   }
   public String toString() { //used for debugging(mainly) with a check for emptiness also
      String str = "";
      for(char c: this.stack) {
         if(c != 0) {str += c;}
      }
      return str;
   }
}
