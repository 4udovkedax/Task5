package ru.task5.operations.SettlementAccount;

import ru.task5.model.SettlementAccountModel;

public interface OperationAccount {
    public String apply(SettlementAccountModel model, String resultModel);
}
