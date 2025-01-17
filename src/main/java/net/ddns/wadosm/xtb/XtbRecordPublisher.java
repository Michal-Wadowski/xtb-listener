package net.ddns.wadosm.xtb;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pro.xstore.api.message.records.STickRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Slf4j
@Service
public class XtbRecordPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final XtbProperties xtbProperties;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @SneakyThrows
    public void publishRecord(STickRecord record) {
        executorService.submit(() -> processRecord(record));
    }

    @SneakyThrows
    private void processRecord(STickRecord record) {
        log.debug("Sending tick  >>>: {}", record);
        String json = objectMapper.writeValueAsString(record);
        kafkaTemplate.send(xtbProperties.getXtb().getTopicName(), json);
        log.debug("done");
    }

}
