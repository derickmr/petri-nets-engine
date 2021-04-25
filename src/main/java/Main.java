import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        Document document;

        try {
            File file = new File("src/test/java/simple-reset.pflow");
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            document = (Document) unmarshaller.unmarshal(file);

            PetriNet petriNet = document.getSubnet().stream().findFirst().get();
            petriNet.initializeArcs();

            Scanner scanner = new Scanner(System.in);

            System.out.println(petriNet.toString());

            while (petriNet.canRun()){
                System.out.println("Aperte \"ENTER\" para continuar...");
                scanner.nextLine();
                petriNet.run();
                System.out.println(petriNet.toString());
            }
            System.out.println("Nenhuma transição habilitada. Processamento encerrado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
