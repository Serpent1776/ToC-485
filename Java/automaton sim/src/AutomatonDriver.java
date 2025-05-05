import java.io.File;
import java.util.Scanner;
public class AutomatonDriver {
    public static void main(String[] args) throws Exception {
        try { //setup
        Scanner scanFile = new Scanner(new File("Java\\automaton sim\\src\\sample1.txt"));
        Scanner responder = new Scanner(System.in);
        AutomatonClass robot = new AutomatonClass(scanFile);
        simulation(robot, responder);
        responder.close();
        } catch(Exception e) { //errors during setup involve formatting
            //System.out.print(e.getMessage()); //meant for debugging
            System.out.print("File is not correctly formated");
            throw e;
        }
    }
    public static void simulation(AutomatonClass robotGraph, Scanner responder) { //simulate the automaton with a text Scanner
        String response;
        boolean end = false;
        while(!end) {
            try {
            System.out.print("Please give us a string and we will take through the automaton: ");
            response = responder.nextLine();
            if(response.equals("LAMBDA")) {
                // LAMBDA STUFF
                response = "";
            }
            if(response.equals("STOP")) {
                end = true; //program temination in process
            }
            robotGraph.simulate(response);
            System.out.println("YES!");
            responder.next();
            } catch(Exception e) {
                //System.out.print(e.getMessage()); //meant for debugging
                System.out.println("NO!"); 
            }
        } 
        responder.close();
    }
    
    
}
 /*
  * Citation if need be for oracle for char:
  */