package by.morozmaksim.deepseektaskservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DeepseekTaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeepseekTaskServiceApplication.class, args);
    }

}
