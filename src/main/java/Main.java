public class Main {
    public static void main (String[] args){
        PetriNet petriNet = new PetriNet();

        //TODO read PNML file and wait ENTER input from user to run

        while (petriNet.canRun()){
            System.out.println(petriNet.toString());
            petriNet.run();
        }
    }
}
