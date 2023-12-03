package config;

import Actions.MatrixActions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatrixConfig {
	@Bean
	public MatrixActions matrixActions() {
		return new MatrixActions();
	}


}