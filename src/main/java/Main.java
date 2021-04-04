public class Main {
    public static void main (String[] args){
        PetriNet petriNet = new PetriNet();

        //TODO read PNML file and wait ENTER input from user to run
        while (true){
            petriNet.print();
            petriNet.run();
        }
    }
}
