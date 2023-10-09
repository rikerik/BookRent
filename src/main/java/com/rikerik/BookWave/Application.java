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
//TODO
    //egyedi prefrencia alapján ajánl könyveket, ehhez kell szűrés
    //pl cimkék, műfaj vagy írok
    //Adminként felhasznélók kezelése

    //könyvklubb létrehozása pl, ha ki lett bérelve egy adott műfajból több könyv, ide lehet kell chat
    //naponta sorsolás adott könyvekből, ha a sorsolt könyv a felhasználó könyvtárában van, akkor kap valami egyedit



}
