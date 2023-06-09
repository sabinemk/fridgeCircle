package com.hiFive.FridgeCircle.entity;

public enum Difficulty {
    EASY("easy"),
    MODERATE("moderate"),
    DIFFICULT("difficult");

    private final String difficultyName;

    private Difficulty(String difficultyName){
        this.difficultyName=difficultyName;
    }

    public String getDifficultyName(){
        return this.difficultyName;
    }
}

