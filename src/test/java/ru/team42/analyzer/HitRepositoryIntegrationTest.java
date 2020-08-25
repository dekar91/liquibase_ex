package ru.team42.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team42.analyzer.repositories.HitRepository;

@SpringBootTest
@DataJpaTest
public class HitRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HitRepository employeeRepository;

    // write test cases here

}