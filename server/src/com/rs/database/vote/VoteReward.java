package com.rs.database.vote;

public class VoteReward
{
  private String playerName;
  private int rewardId;

  public VoteReward(String p, int r) {
    this.playerName = p;
    this.rewardId = r;
    System.out.println(p + " has voted and been rewarded with reward id: " + r + ".");
  }

  public String getPlayerName() {
    return this.playerName;
  }

  public int getReward() {
    return this.rewardId;
  }
}