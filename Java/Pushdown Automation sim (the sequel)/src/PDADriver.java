import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
public class PDADriver {
    public static void main(String[] args) throws Exception {
        try {
        /*
        ThePDA syntax:
        first line: the maximum number of states. That string is parsed into an int, 
        so just leave a number for this (that is your total number of states).
        second line: the language of the pda, signa. 
        That string is turned into a char array so no need for commas.
        Make it like this "ab" instead of putting "a, b".
        third and on:
        These are strings that are split with the regex "; " become either states or transitions for states.

        States start with a number, "INITAL", or "FINAL" to signify their position. 
        in the latter case, the first number in the State.
        other numbers after the first one are adjacent.

        transitions start with "transition".
        first two numbers after are the start and end of the transition. The three symbols are the read, pop, and push.
        Notes:
            Note 1: start with your pda's inital state and make sure a final state does exist.
            Note 2: Also, make sure your pda's inital state has adjacent states.
            Note 3: reads are chars, so don't use "" or null for "", instead use LAMBDA to indicate empty spots.
            Note 4: Please make sure reads are in the language and either the symbols or LAMBDA.
            Note 5: No need to have duplicate adjacent states as adjacent states for ant given state. 
            eg: this is no need (5; 5; 5; 3) as (5; 5; 3) is a good state initalization already.
            Note 6: writing exit anywhere in your reply ends the program instantly.
            Note 7: "end" ends your text file; this will cause bugs if you don't have it.
        */
        //Part One: Initalization
        Scanner fileScanner = new Scanner(new File("src\\ThePDA.text"));
        Scanner user = new Scanner(System.in);
        ArrayList<String> fileBox = new ArrayList<String>();
        String filePart = fileScanner.nextLine();
        while(!filePart.equals("end")) {
            fileBox.add(filePart);
            filePart = fileScanner.nextLine();
        }
        fileScanner.close();
        /*for(String s: fileBox) {
        System.out.println(s);
        } for debugging*/ 
        PDAStack stack = new PDAStack(); //a stack
        int size = Integer.parseInt(fileBox.get(0));
        char[] language = fileBox.get(1).toCharArray();
        PushDownAutomation pda = new PushDownAutomation(size, language);
        for(int i = 2; i < fileBox.size(); i++) {
            if(fileBox.get(i).contains("transition")) {
                pda.addTransitions(fileBox.get(i).split("; "));
            } else {
                pda.addState(fileBox.get(i).split("; "));
            }
        }
        System.out.println("Critical Success: Pushdown Automation initalized. Testing can now begin.");
        //Final Part: The Loop
        while(true) {
            System.out.print("Give a string to test your automata or exit by saying it: ");
            String response = user.nextLine(); //your response is tested.
            if(response.toLowerCase().contains("exit")) { //exits the loop and program, immediately.
                break;
            }
            try {
            if(pda.test(response, stack)) {response = "YES";} else {response = "NO";} 
            //respond to have your automation tested with your own unique regex!
            //"NO" will be sent if it's outside the automation, "YES" otherwise unless exceptions contain "please"
            //exceptions are meant as more serious if they contain "please" 
            //Please address the issue at hand in the text if need be.
            } catch(Exception e) {
                //throw e; for debugging purposes meant to throw PDAExceptions
                // other exceptions are meant to be handled if possible
                if(!e.getMessage().contains("please")) { //used to help the user, also for serious errors.
                    // best to exit if you see them while running.
                response = "NO";
                } else {
                response = e.getMessage();
                }
            }
            System.out.println(response);
            stack = new PDAStack(); //refresh to prevent bugs from rearing their ugly faces.
        }
        user.close();
    } catch (Exception e) {
        //throw e; debugging
       System.out.println("Error: Unsuccessful creation of a PDA. Please look a your text file a follow the syntax. \n Or, you used ctrl c next time you can respond with \"exit\" to exit properly.");
    }
}
}
