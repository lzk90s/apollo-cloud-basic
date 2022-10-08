package team.javis.apollo.basic.gateway.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author : qihang.liu
 * @date 2022-01-18
 */
@Data
@ConfigurationProperties(prefix = "secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;

    String[] getUrlsArray() {
        return urls.toArray(new String[]{});
    }
}
