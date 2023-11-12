package com.rikerik.BookWave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }

//TODO
    //chat tematikák elkészítése és bérelt könyvek alapján besorolás
    //mennyiség a könyveknél
    //admin felület a különböző tevékenységek elvégzésére
    //a keresésnél ne csak pontos találat esetén adjon választ (pl kisbetű nagybetű között nincs különbség)
}
