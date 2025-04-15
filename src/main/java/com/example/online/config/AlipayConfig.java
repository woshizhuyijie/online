import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayConfig {

    private String appId;
    private String privateKey;
    private String publicKey;
    private String gatewayUrl;
    private String notifyUrl;
    private String returnUrl;

    public AlipayClient getClient() {
        return new DefaultAlipayClient(
            gatewayUrl,
            appId,
            privateKey,
            "json",
            "UTF-8",
            publicKey,
            "RSA2"
        );
    }
}
