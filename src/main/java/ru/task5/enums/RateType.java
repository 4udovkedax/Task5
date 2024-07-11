package ru.task5.enums;

public enum RateType {
    прогрессивная(1), дифференцированная(0);
    private int stav;

    RateType(int stav) {
        this.stav = stav;
    }
}
