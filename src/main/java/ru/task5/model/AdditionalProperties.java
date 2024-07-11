package ru.task5.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdditionalProperties {
    List<Data> data = new ArrayList<>();
}
