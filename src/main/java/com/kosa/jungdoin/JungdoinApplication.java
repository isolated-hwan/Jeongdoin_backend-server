package com.kosa.jungdoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JungdoinApplication {

    public static void main(String[] args) {
        SpringApplication.run(JungdoinApplication.class, args);
    }

}
