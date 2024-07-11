package ru.task5.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ResultModel extends ResModel{
    private List<String> registerId = new ArrayList<>();
    private List<String> supplementaryAgreementId = new ArrayList<>();
}
