package ru.task5.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task5.model.SettlementAccountModel;
import ru.task5.operations.SettlementAccount.OperationAccount;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementAccountService {
    @Autowired
    List<OperationAccount> operAccounts = new ArrayList<>();

    @Getter
    private String resultModel;

    public SettlementAccountService(List<OperationAccount> operationAccounts) {
        this.operAccounts = operationAccounts;
    }

    @Transactional
    public void startOperations(SettlementAccountModel model) {
        for (OperationAccount o : operAccounts) {
            resultModel = o.apply(model, resultModel);
        }
    }
}
