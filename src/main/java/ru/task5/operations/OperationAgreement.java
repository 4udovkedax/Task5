package ru.task5.operations;

import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;

public interface OperationAgreement {
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel);
}
