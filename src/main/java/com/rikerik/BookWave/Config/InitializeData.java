package com.rikerik.BookWave.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class InitializeData {

    @Autowired
    private DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        // Load the SQL script
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("InitData.sql"));

        // Encrypt the passwords
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword1 = passwordEncoder.encode("erik1");
        String encryptedPassword2 = passwordEncoder.encode("admin");
        String encryptedPassword3 = passwordEncoder.encode("test");

        // Populate the database with the script
        resourceDatabasePopulator.setContinueOnError(true);
        resourceDatabasePopulator.execute(dataSource);

        // Update the passwords to encrypted values
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE users SET password = ? WHERE username = ?", encryptedPassword1, "Erik");
        jdbcTemplate.update("UPDATE users SET password = ? WHERE username = ?", encryptedPassword2, "Admin");
        jdbcTemplate.update("UPDATE users SET password = ? WHERE username = ?", encryptedPassword3, "Test");
    }
}
