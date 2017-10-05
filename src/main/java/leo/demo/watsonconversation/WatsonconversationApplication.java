package leo.demo.watsonconversation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WatsonconversationApplication {
	public static final String DEVELOPMENT_VERSION = "0.0.2";
	public static final long APPLICATION_START_TIME = System.currentTimeMillis();

	public static void main(String[] args) {
		SpringApplication.run(WatsonconversationApplication.class, args);
	}
}
