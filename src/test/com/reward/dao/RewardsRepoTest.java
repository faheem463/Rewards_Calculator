package com.reward.dao;

import com.reward.dao.RewardRepository;
import com.reward.model.RewardPoint;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class RewardsRepoTest {

    @Mock
    RewardRepository rewardRepository;

    @Test
    public void testSaveMethod() {
        RewardPoint rd = new RewardPoint();

        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(121.0);
        rewardRepository.save(rd);

        Assert.assertNotNull(rd);

        assertNotNull(rd.getTransactionPrice());
        assertEquals(rd.getTransactionPrice(), 121.0);
    }

    @Test
    public void testFetchAll() {
        Iterable<RewardPoint> players = rewardRepository.findAll();
        assertThat(players).isEmpty();

    }
}
