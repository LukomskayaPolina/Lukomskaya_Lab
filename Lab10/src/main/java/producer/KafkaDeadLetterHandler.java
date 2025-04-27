package producer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaDeadLetterHandler {

    private KafkaProducer<String, String> producer;

    public KafkaDeadLetterHandler(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    public void handleFailedMessage(String topic, String key, String message) {
        try {
            producer.send(new ProducerRecord<>("dead_letter_topic", key, message), (metadata, exception) -> {
                if (exception != null) {
                    System.err.println("Ошибка отправки в DLT: " + exception.getMessage());
                } else {
                    System.out.printf("Сообщение отправлено в DLT: топик = %s, раздел = %d, смещение = %d%n",
                            metadata.topic(), metadata.partition(), metadata.offset());
                }
            });
        } catch (Exception e) {
            System.err.println("Не удалось отправить сообщение в DLT: " + e.getMessage());
        }
    }

}
