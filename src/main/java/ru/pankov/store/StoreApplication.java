package ru.pankov.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@SpringBootApplication
@PropertySource("classpath:secret.properties")
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);

		new Scanner(System.in).nextLine();
		System.exit(1);
	}

}
