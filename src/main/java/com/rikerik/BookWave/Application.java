package com.rikerik.BookWave;

import com.rikerik.BookWave.Controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication

public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }


}
