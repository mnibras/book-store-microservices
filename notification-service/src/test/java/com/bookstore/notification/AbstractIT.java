package com.bookstore.notification;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class AbstractIT {


}
