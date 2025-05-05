package producer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class KafkaProducerExample {

    public static void main(String[] args) {
        // Настройка Kafka-продюсера
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Создаем Kafka-продюсер
        Producer<String, String> producer = new KafkaProducer<>(props);

        System.out.println("Kafka-продюсер запущен. Введите данные для отправки.");

        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                // Ввод данных
                System.out.print("Введите user_id: ");
                String userId = scanner.nextLine();

                System.out.print("Введите действие (например, 'purchase'): ");
                String action = scanner.nextLine();

                System.out.print("Введите timestamp (например, '2023-10-01T12:00:00'): ");
                String timestamp = scanner.nextLine();

                // Формирование JSON-сообщения
                String message = String.format(
                        "{\"user_id\": \"%s\", \"action\": \"%s\", \"timestamp\": \"%s\"}",
                        userId, action, timestamp
                );

                // Отправка сообщения в топик
                producer.send(new ProducerRecord<>("user_actions", message), (metadata, exception) -> {
                    if (exception != null) {
                        exception.printStackTrace();
                    } else {
                        System.out.printf("Сообщение отправлено в топик %s раздел %d с offset %d%n",
                                metadata.topic(), metadata.partition(), metadata.offset());
                    }

                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
            scanner.close();
        }
    }
}
