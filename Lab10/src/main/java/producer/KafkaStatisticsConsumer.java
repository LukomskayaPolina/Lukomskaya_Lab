package producer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import storage.SQLiteStorage;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class KafkaStatisticsConsumer {

    public static void main(String[] args) throws Exception {
        // Подключение к SQLite
        SQLiteStorage storage = new SQLiteStorage("D:\\3 курс\\Средства и технологии анализа\\Lukomskaya_Lab\\Lab10\\messages.db");

        // Настройка Kafka Consumer
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "statistics-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList("user_actions"));

        System.out.println("Запуск консьюмера...");

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    String userId = parseField(message, "user_id");
                    String action = parseField(message, "action");
                    String timestamp = parseField(message, "timestamp");

                    // Сохраняем сообщение в SQLite
                    storage.saveMessage(userId, action, timestamp);
                }

                // Отображаем содержимое базы данных
                storage.displayMessages();
            }
        } finally {
            consumer.close();
            storage.close();
        }
    }

    private static String parseField(String jsonMessage, String field) {
        int fieldIndex = jsonMessage.indexOf("\"" + field + "\":");
        if (fieldIndex != -1) {
            int start = jsonMessage.indexOf("\"", fieldIndex + field.length() + 3) + 1;
            int end = jsonMessage.indexOf("\"", start);
            return jsonMessage.substring(start, end);
        }
        return null;
    }
}

