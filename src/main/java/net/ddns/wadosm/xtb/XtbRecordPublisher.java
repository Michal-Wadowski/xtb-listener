package net.ddns.wadosm.xtb;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pro.xstore.api.message.records.STickRecord;

@RequiredArgsConstructor
@Slf4j
@Service
public class XtbRecordPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final XtbProperties xtbProperties;

    @SneakyThrows
    public void publishRecord(STickRecord record) {
        log.debug("Sending tick  >>>: {}", record);
        String json = objectMapper.writeValueAsString(record);
        kafkaTemplate.send(xtbProperties.getXtb().getTopicName(), json);
        log.debug("done");
    }

}
