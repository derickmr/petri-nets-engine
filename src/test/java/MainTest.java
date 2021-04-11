import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;


public class MainTest {
    @Test
    public void testTarefa3()
    {
        File file = new File("src/test/java/tarefa_3.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Document document = (Document) unmarshaller.unmarshal(file);
            assertNotNull(document.getNets());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}