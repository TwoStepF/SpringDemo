package com.example.opentalk.repository;
import static org.assertj.core.api.Assertions.assertThat;
import com.example.opentalk.entity.Opentalk_topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OpentalkRepositoryTest {
    @Autowired
    private OpentalkRepository opentalkRepository;

    @Test
    public void whenFindByid_thenReturnOpentalk() {

        Opentalk_topic expectOpentalk = new Opentalk_topic(2L, "khanh", null, null, null);

        Opentalk_topic actualOpentalk = opentalkRepository.findOpentalkByID(2L);

        assertThat(actualOpentalk).usingRecursiveComparison().isEqualTo(expectOpentalk);
    }

    @Test
    public void shouldSaveOpentalk() {
        Opentalk_topic expectOpentalk = new Opentalk_topic(1L, "khanh", null, null, null);
        Opentalk_topic actualOpentalk = opentalkRepository.save(expectOpentalk);;
        assertThat(actualOpentalk).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectOpentalk);
    }
}
