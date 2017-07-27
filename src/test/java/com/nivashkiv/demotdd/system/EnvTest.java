package com.nivashkiv.demotdd.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EnvTest {

    @Autowired
    DataSource dataSource;

    @Test
    void testDB() throws SQLException {
        dataSource.getConnection();
        assertThat(dataSource.getConnection().getMetaData().getDatabaseProductName(), is("H2"));
    }
}