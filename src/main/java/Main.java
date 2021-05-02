import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        if (args.length < 1){
            return;
        }

        String path = args[0];

        Document document;
        int cycle = 0;

        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            document = (Document) unmarshaller.unmarshal(file);

            PetriNet petriNet = document.getSubnet().stream().findFirst().get();
            petriNet.initializeArcs();

            Scanner scanner = new Scanner(System.in);

            System.out.println(petriNet.toString(cycle));

            while (petriNet.canRun()){
                System.out.println("Aperte \"ENTER\" para continuar...");
                scanner.nextLine();
                petriNet.run();
                cycle++;
                System.out.println(petriNet.toString(cycle));
            }
            System.out.println("Nenhuma transição habilitada. Processamento encerrado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
