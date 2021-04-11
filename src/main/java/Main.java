import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Main {
    public static void main (String[] args){
        PetriNet petriNet = new PetriNet();

        //TODO read PNML file and wait ENTER input from user to run

        /* Mock starts */
        Place place = new Place();
        Place place2 = new Place();

        Token token1 = new Token();
        Token token2 = new Token();

        Transition transition = new Transition();
        Transition transition2 = new Transition();
        transition.setId("1");
        transition2.setId("2");

        Stack<Token> tokens = new Stack<>();
        tokens.push(token1);
        tokens.push(token2);

        place.setId("1");
        place.setTokens(tokens);

        place2.setId("2");
        place2.setTokens(tokens);

        petriNet.setPlaces(Arrays.asList(place, place2));
        petriNet.setTransitions(Arrays.asList(transition, transition2));

        System.out.println(petriNet.toString());

        /* Mock ends */

//        while (true){
//            System.out.println(petriNet.toString());
//            petriNet.run();
//        }
    }
}
