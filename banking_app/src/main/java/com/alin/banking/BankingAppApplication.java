package com.alin.banking;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alin.banking.model.Account;
import com.alin.banking.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(AccountRepository accountRepository){
//		return args -> {
//			Account test = new Account("RO001","Alin",100.0);
//			accountRepository.save(test);
//			System.out.println("Contul de test a fost creat!");
//		};
//	}

}
