import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;


public class UnmarshallerTest {
    @Test
    public void testTarefa3()
    {
        File file = new File("src/test/java/ra e princesa.pflow");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Document document = (Document) unmarshaller.unmarshal(file);
            assertNotNull(document.getSubnet());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}