package net.ddns.wadosm.xtb;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pro.xstore.api.sync.ServerData;

import java.util.List;

@ConfigurationProperties
@Getter
@Setter
@NoArgsConstructor
public class XtbProperties {
    private Xtb xtb;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Xtb {
        private ServerData.ServerEnum serverEnum;
        private List<String> subscribePrices;

        private String login;
        private String password;
    }
}
