import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Date;
import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        String clientId = "my-producer";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
        props.put("client.id", clientId);

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        int numOfRecords = 100;
        String topic = "strings";

        // Example 1 - Numbers as strings for key and value, no delay
        for (int i = 0; i < numOfRecords; i++) {
            System.out.println("Message " + i + " was just sent");
            producer.send(new ProducerRecord<>("numbers", Integer.toString(i), Integer.toString(i)));
        }
        producer.close();

        // Example 2 - Formatted string as message. 300 ms delay for sending
        try {
            for (int i = 0; i < numOfRecords; i++) {
                String message = String.format("Producer %s sent message %s at %s", clientId, i, new Date());
                System.out.println(message);
                producer.send(new ProducerRecord<>(topic, Integer.toString(i), message));
                Thread.sleep(300);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }
}
