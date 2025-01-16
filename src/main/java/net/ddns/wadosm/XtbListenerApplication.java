package net.ddns.wadosm;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ddns.wadosm.xtb.XtbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({
        XtbProperties.class
})
public class XtbListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XtbListenerApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
