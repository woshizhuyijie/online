import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaTestController {

    private final KafkaProducerService producerService;

    @PostMapping("/send")
    public String sendKafkaMessage(@RequestParam String msg) {
        producerService.sendMessage("order-topic", msg);
        return "消息已发送到 Kafka: " + msg;
    }
}
