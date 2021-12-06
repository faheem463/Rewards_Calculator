package com.reward.dao;

import com.reward.model.RewardPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository  extends JpaRepository<RewardPoint, Long> {

}