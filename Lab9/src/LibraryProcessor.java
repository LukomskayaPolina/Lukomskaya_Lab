import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LibraryProcessor {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/library.xml");

            // Парсинг XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Получаем список книг
            NodeList bookList = doc.getElementsByTagName("book");
            List<Element> books = new ArrayList<>();

            double totalPrice = 0;

            System.out.println("Список книг:");
            for (int i = 0; i < bookList.getLength(); i++) {
                Node book = bookList.item(i);
                if (book.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) book;
                    books.add(bookElement);

                    String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                    String author = bookElement.getElementsByTagName("author").item(0).getTextContent();
                    int year = Integer.parseInt(bookElement.getElementsByTagName("year").item(0).getTextContent());
                    String genre = bookElement.getElementsByTagName("genre").item(0).getTextContent();
                    double price = Double.parseDouble(bookElement.getElementsByTagName("price").item(0).getTextContent());

                    System.out.println("Название: " + title + ", Автор: " + author +
                            ", Год: " + year + ", Жанр: " + genre + ", Цена: " + price + " руб.");
                    totalPrice += price;
                }
            }

            // Средняя цена
            double averagePrice = totalPrice / bookList.getLength();
            System.out.println("Средняя цена книг: " + averagePrice + " руб.");

            // Фильтруем книги по жанру
            String filterGenre = "Фэнтези";
            System.out.println("\nКниги жанра " + filterGenre + ":");
            for (Element book : books) {
                String genre = book.getElementsByTagName("genre").item(0).getTextContent();
                if (genre.equalsIgnoreCase(filterGenre)) {
                    String title = book.getElementsByTagName("title").item(0).getTextContent();
                    String author = book.getElementsByTagName("author").item(0).getTextContent();
                    System.out.println("Название: " + title + ", Автор: " + author);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
