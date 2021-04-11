import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Main {
    public static void main (String[] args){
        PetriNet petriNet = new PetriNet();

        //TODO read PNML file and wait ENTER input from user to run

        while (true){
            System.out.println(petriNet.toString());
            petriNet.run();
        }
    }
}
