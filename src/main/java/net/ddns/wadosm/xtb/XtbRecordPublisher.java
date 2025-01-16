package net.ddns.wadosm.xtb;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pro.xstore.api.message.records.STickRecord;

@RequiredArgsConstructor
@Slf4j
@Service
public class XtbRecordPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${xtb.topicName}")
    private String topicName;

    @SneakyThrows
    public void publishRecord(STickRecord record) {
        String json = objectMapper.writeValueAsString(record);
        kafkaTemplate.send(topicName, json);
        log.debug("Stream tick record: {}", record);
    }

}
