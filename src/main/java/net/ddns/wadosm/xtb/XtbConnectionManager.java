package net.ddns.wadosm.xtb;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.xstore.api.message.command.APICommandFactory;
import pro.xstore.api.message.response.LoginResponse;
import pro.xstore.api.sync.Credentials;
import pro.xstore.api.sync.SyncAPIConnector;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class XtbConnectionManager {

    private final Credentials xtbCredentials;
    private final XtbProperties xtbProperties;
    private final XtbRecordPublisher xtbRecordPublisher;

    private XtbConnectorWrapper connectorWrapper = null;

    /**
     * Short time connection overlapping is intended
     */
    @Scheduled(fixedDelayString = "${xtb.reconnectPeriod}")
    public void startNewListeningAndOptionallyStopPreviousOne() {
        var xtbConnectorWrapper = startListening();

        optionallyStopListening();

        this.connectorWrapper = xtbConnectorWrapper;
    }

    private XtbConnectorWrapper startListening() {
        SyncAPIConnector xtbConnector = getSyncApiConnector(xtbCredentials);
        var connectorWrapper = new XtbConnectorWrapper(xtbConnector, xtbProperties, xtbRecordPublisher);
        connectorWrapper.startListening();
        return connectorWrapper;
    }

    @PreDestroy
    public void optionallyStopListening() {
        if (connectorWrapper != null) {
            connectorWrapper.stopListening();
            connectorWrapper = null;
        }
    }

    @SneakyThrows
    private SyncAPIConnector getSyncApiConnector(Credentials xtbCredentials) {
        SyncAPIConnector connector;
        try {
            connector = new SyncAPIConnector(xtbProperties.getXtb().getServerEnum());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginResponse loginResponse = APICommandFactory.executeLoginCommand(connector, xtbCredentials);

        if (loginResponse != null && loginResponse.getStatus()) {
            return connector;
        } else {
            throw new RuntimeException("Can't connect to XTB for some reason");
        }
    }

}
