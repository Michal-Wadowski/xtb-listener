package net.ddns.wadosm;

import net.ddns.wadosm.xtb.XtbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        XtbProperties.class
})
public class XtbListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XtbListenerApplication.class, args);
    }

}
