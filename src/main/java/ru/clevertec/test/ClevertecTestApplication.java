package ru.clevertec.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import ru.clevertec.test.repository.dao.fake.UserDAO;
import ru.clevertec.test.repository.dao.fake.UserDaoImpl;
import ru.clevertec.test.repository.entity.User;
import ru.clevertec.test.service.impl.UserService;
import ru.clevertec.test.service.impl.UserServiceImpl;

@SpringBootApplication
public class ClevertecTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClevertecTestApplication.class, args);
    }

}
