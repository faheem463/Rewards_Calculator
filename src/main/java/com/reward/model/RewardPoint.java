package com.reward.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reward")
public class RewardPoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rewardId;

    @Column(name = "trns_price")
    private double transactionPrice;

    @Column(name = "trns_dt")
    @CreationTimestamp
    private Date transactionDate;

    @Column(name = "rewards")
    private int rewards;

    public RewardPoint( ) {
    }

    public RewardPoint(double transactionPrice, Date transactionDate, int rewards) {
        this.transactionPrice = transactionPrice;
        this.transactionDate = transactionDate;
        this.rewards = rewards;
    }


    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }


    public double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getRewards() {
        return rewards;
    }

    public void setRewards(int rewards) {
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return "RewardPointBean{" +
                "transactionPrice=" + transactionPrice +
                ", transactionDate=" + transactionDate +
                ", rewards=" + rewards +
                '}';
    }
}
