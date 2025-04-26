import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public class XMLValidator {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("library.xml");
            File xsdFile = new File("library.xsd");

            // Создаем фабрику для загрузки схемы
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);

            // Валидатор
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            System.out.println("XML документ валиден.");
        } catch (SAXException e) {
            System.out.println("Ошибка: XML не валиден. " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
