package net.ddns.wadosm.xtb;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.xstore.api.message.error.APICommunicationException;
import pro.xstore.api.message.records.STickRecord;
import pro.xstore.api.message.records.STradeRecord;
import pro.xstore.api.streaming.StreamingListener;
import pro.xstore.api.sync.SyncAPIConnector;

import java.io.IOException;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class XtbConnectionManager {

    private final SyncAPIConnector xtbConnector;
    private final XtbProperties xtbProperties;
    private final XtbRecordPublisher xtbRecordPublisher;

    @PostConstruct
    public void startListening() throws APICommunicationException, IOException {
        xtbConnector.connectStream(new TickRecordOnlyListenerAdapter(xtbRecordPublisher::publishRecord));
        log.info("XTB Stream connected.");

        getXtbPropertiesXtb()
                .getSubscribePrices()
                .forEach(this::subscribedPrice);
    }

    private XtbProperties.Xtb getXtbPropertiesXtb() {
        return xtbProperties.getXtb();
    }

    @SneakyThrows
    private void subscribedPrice(String symbol) {
        xtbConnector.subscribePrice(symbol);
    }

    @SneakyThrows
    private void unsubscribePrice(String symbol) {
        xtbConnector.unsubscribePrice(symbol);
    }

    @SneakyThrows
    @PreDestroy
    public void stopListening() {
        getXtbPropertiesXtb()
                .getSubscribePrices()
                .forEach(this::unsubscribePrice);
        xtbConnector.disconnectStream();
        log.info("XTB Stream disconnected.");
    }

    @RequiredArgsConstructor
    private static class TickRecordOnlyListenerAdapter extends StreamingListener {

        private final Consumer<STickRecord> consumer;

        public void receiveTradeRecord(STradeRecord tradeRecord) {
            throw new UnsupportedOperationException("Unsupported receiveTradeRecord(STradeRecord)");
        }

        public void receiveTickRecord(STickRecord tickRecord) {
            consumer.accept(tickRecord);
        }
    }
}
