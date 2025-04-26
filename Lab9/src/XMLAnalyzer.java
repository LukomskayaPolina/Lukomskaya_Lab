import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class XMLAnalyzer {
    public static void main(String[] args) {
        try {
            // Укажите путь к вашему XML-документу
            File xmlFile = new File("src/sample.xml"); // Укажите относительный путь к файлу в проекте.


            // Парсинг XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Вывод корневого элемента
            System.out.println("Корневой элемент: " + doc.getDocumentElement().getNodeName());

            // Рекурсивный анализ всех элементов
            System.out.println("Анализ элементов:");
            analyzeNode(doc.getDocumentElement(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Рекурсивная функция для анализа узлов XML
    private static void analyzeNode(Node node, int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            // Отступы для визуализации уровня вложенности
            String indent = "  ".repeat(depth);

            // Имя текущего элемента
            System.out.println(indent + "Элемент: " + node.getNodeName());

            // Показ атрибутов, если есть
            NamedNodeMap attributes = node.getAttributes();
            if (attributes != null && attributes.getLength() > 0) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attr = attributes.item(i);
                    System.out.println(indent + "  Атрибут: " + attr.getNodeName() + " = " + attr.getNodeValue());
                }
            }

            // Обработка дочерних элементов
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                analyzeNode(children.item(i), depth + 1);
            }
        }
    }
}
