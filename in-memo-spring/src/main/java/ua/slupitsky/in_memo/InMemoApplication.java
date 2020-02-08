package ua.slupitsky.in_memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Logger;

@SpringBootApplication
public class InMemoApplication {

	private static final Logger logger = Logger.getLogger(InMemoApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(InMemoApplication.class, args);
		logger.info("--Application Started--");
	}

}

