package net.ddns.wadosm.xtb;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pro.xstore.api.message.records.STickRecord;
import pro.xstore.api.message.records.STradeRecord;
import pro.xstore.api.streaming.StreamingListener;
import pro.xstore.api.sync.SyncAPIConnector;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
class XtbConnectorWrapper {

    private final SyncAPIConnector xtbConnector;
    private final XtbProperties xtbProperties;
    private final XtbRecordPublisher xtbRecordPublisher;

    @SneakyThrows
    public void startListening() {
        log.info("Connecting to XTB");
        xtbConnector.connectStream(new TickRecordOnlyListenerAdapter(xtbRecordPublisher::publishRecord));
        log.info("XTB Stream connected");

        getXtbPropertiesXtb()
                .getSubscribePrices()
                .forEach(this::subscribedPrice);
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
    public void stopListening() {
        getXtbPropertiesXtb()
                .getSubscribePrices()
                .forEach(this::unsubscribePrice);
        xtbConnector.disconnectStream();
        log.info("XTB Stream disconnected");
    }

    private XtbProperties.Xtb getXtbPropertiesXtb() {
        return xtbProperties.getXtb();
    }

    @RequiredArgsConstructor
    @Slf4j
    private static class TickRecordOnlyListenerAdapter extends StreamingListener {

        private final Consumer<STickRecord> consumer;

        public void receiveTradeRecord(STradeRecord tradeRecord) {
            throw new UnsupportedOperationException("Unsupported receiveTradeRecord(STradeRecord)");
        }

        public void receiveTickRecord(STickRecord tickRecord) {
            log.debug("received XTB rick record");
            consumer.accept(tickRecord);
            log.debug("done");
        }
    }

}
