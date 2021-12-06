package com.reward.cotroller;

import com.reward.model.RewardPoint;
import com.reward.service.RewardsPointCalcService;
import com.reward.exception.RewardsPointServiceCalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class RewardsPointController {

    @Autowired
    private RewardsPointCalcService rewardsPointCalcService;

    @PostMapping("/calculatereward")
    public ResponseEntity<String> calculateReward(@RequestBody RewardPoint reward) throws RewardsPointServiceCalException {
        try {
            return new ResponseEntity<>("Successfully Generated Reward with ID" +
                    rewardsPointCalcService.calculateRewards(reward).getRewardId(),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getrewards")
    public ResponseEntity<List<RewardPoint>> fetchAllRewards() throws RewardsPointServiceCalException {
        try {
            return new ResponseEntity<>(rewardsPointCalcService.fetchAllRewards(),
                    HttpStatus.OK);
        } catch (RewardsPointServiceCalException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/reward/{id}")
    public ResponseEntity<RewardPoint> getRewardById(@PathVariable("id") Integer rewardId)
            throws RewardsPointServiceCalException {
        ResponseEntity<RewardPoint> result;
        try {
            result = new ResponseEntity<>(rewardsPointCalcService
                    .getRewardById(rewardId), HttpStatus.OK);
        } catch (Exception e) {
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
