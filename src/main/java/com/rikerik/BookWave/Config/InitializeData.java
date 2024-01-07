package com.rikerik.BookWave.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

//Class which fills the tables with pre-defined data for testing

@Component
public class InitializeData {

    @Autowired
    private DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        /* Only for testing and filling the db if its empty
        // Load the SQL script for users
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("InitUserData.sql"));

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

        //running the script which loads the books

        resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("InitBookData.sql"));

        resourceDatabasePopulator.setContinueOnError(true);
        resourceDatabasePopulator.execute(dataSource);

         */
    }
}
