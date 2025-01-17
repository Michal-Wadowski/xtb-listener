package net.ddns.wadosm.xtb;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.xstore.api.sync.Credentials;

@Configuration
@RequiredArgsConstructor
public class XtbConfiguration {

    private final XtbProperties xtbProperties;

    @Bean
    public Credentials xtbCredentials() {
        var xtb = xtbProperties.getXtb();
        return new Credentials(xtb.getLogin(), xtb.getPassword(), null, null);
    }
}
