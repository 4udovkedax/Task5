package ru.task5.enums;

public enum CoefficientAction {
    повышающий("+"), понижающий("-");
    String coefficient;

    CoefficientAction(String coefficient) {
        this.coefficient = coefficient;
    }
}
