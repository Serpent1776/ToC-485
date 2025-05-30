import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
public class PDADriver {
    public static void main(String[] args) throws Exception {
        try {
        
        //Part One: Initalization
        Scanner fileScanner = new Scanner(new File("src\\ThePDA.txt")); //change this to the path for your PDA simulation txt
        //make sure the follow ThePDA syntax and Notes to allow my program to do its work.
        Scanner user = new Scanner(System.in);
        ArrayList<String> fileBox = new ArrayList<String>();//the file's box
        String filePart = fileScanner.nextLine(); //parts of the file
        while(!filePart.equals("end")) {//file boxing time
            fileBox.add(filePart);
            filePart = fileScanner.nextLine();
        }
        fileScanner.close(); //closing to prevent any damage.
        /*for(String s: fileBox) {
        System.out.println(s);
        } for debugging*/ 
        PDAStack stack = new PDAStack(); //a stack
        int size = Integer.parseInt(fileBox.get(0)); //the maximum number of states
        char[] language = fileBox.get(1).toCharArray(); //the language of the PDA
        PushDownAutomation pda = new PushDownAutomation(size, language);
        for(int i = 2; i < fileBox.size(); i++) { //splitting of the third line and beyond to make the automation
            if(fileBox.get(i).contains("transition")) {
                pda.addTransition(fileBox.get(i).split("; "));
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
            //Please address the issue at hand in your txt if need be.
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
        user.close(); //no leek after it's finished
    } catch (Exception e) {
        //throw e; debugging
       System.out.println("Error: Unsuccessful creation of a PDA. Please look a your text file a follow the syntax. \n Or, you used ctrl c. Next time, you can respond with \"exit\" to exit properly.");
    }
}
}
