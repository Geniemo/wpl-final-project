package wpl.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Getter
public class AppProperties {

    private final FileProperties fileProperties = new FileProperties();

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static final class FileProperties {
        private String root;
    }
}
