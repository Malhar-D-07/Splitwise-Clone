package org.example.splitwise;

import org.example.splitwise.commands.Command;
import org.example.splitwise.commands.CommandExecutor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class SplitwiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CommandExecutor commandExecutor) {
		return runner -> {
			executeCommands(commandExecutor);
		};
	}

	public void executeCommands(CommandExecutor commandExecutor) {
		commandExecutor.runCommand();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
