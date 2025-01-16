package net.ddns.wadosm.xtb;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.xstore.api.message.command.APICommandFactory;
import pro.xstore.api.message.response.LoginResponse;
import pro.xstore.api.sync.Credentials;
import pro.xstore.api.sync.SyncAPIConnector;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class XtbConfiguration {

    private final XtbProperties xtbProperties;

    @Bean
    Credentials xtbCredentials() {
        var xtb = xtbProperties.getXtb();
        return new Credentials(xtb.getLogin(), xtb.getPassword(), null, null);
    }

    @SneakyThrows
    @Bean
    SyncAPIConnector xtbConnector(Credentials xtbCredentials) {
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
