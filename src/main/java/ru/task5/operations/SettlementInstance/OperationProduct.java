package ru.task5.operations.SettlementInstance;

import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;

public interface OperationProduct {
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel);
}
