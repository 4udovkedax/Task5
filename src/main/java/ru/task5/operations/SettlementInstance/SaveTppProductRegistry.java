package ru.task5.operations.SettlementInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import ru.task5.entity.Account;
import ru.task5.entity.AccountPool;
import ru.task5.entity.TppProductRegister;
import ru.task5.entity.TppRefProductRegisterType;
import ru.task5.enums.State;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.repositories.AccountPoolRepo;
import ru.task5.repositories.AccountRepo;
import ru.task5.repositories.TppProductRegisterRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(500)
public class SaveTppProductRegistry implements OperationProduct{
    @Autowired
    TppProductRegisterRepo tppProductRegisterRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    AccountPoolRepo accountPoolRepo;

    @Override
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel) {
        List<String> registerId = new ArrayList<>();
        Account account;
        AccountPool accountPool = new AccountPool();

        accountPool.setBranchCode(model.getBranchCode());
        accountPool.setCurrencyCode(model.getIsoCurrencyCode());
        accountPool.setMdmCode(model.getMdmCode());
        accountPool.setRegistryTypeCode(model.getRegisterType());

        Example<AccountPool> example = Example.of(accountPool);
        Optional<AccountPool> accountPoolOptional = accountPoolRepo.findOne(example);
        account = accountRepo.getReferenceById(accountPoolOptional.get().getId());

        for (TppRefProductRegisterType regTyp: model.getRefProductRegisterTypes()) {
            TppProductRegister tppProductRegister = new TppProductRegister();
            tppProductRegister.setProductId(model.getInstanceId());
            tppProductRegister.setTppRefProductRegisterType(regTyp);
            tppProductRegister.setState(State.Открыт.name());
            tppProductRegister.setCurrencyCode(model.getIsoCurrencyCode());
            tppProductRegister.setAccount(account.getId());
            tppProductRegister.setAccountNumber(account.getAccountNumber());

            tppProductRegisterRepo.save(tppProductRegister);
            registerId.add(String.valueOf(tppProductRegister.getId()));
        }

        resultModel.setRegisterId(registerId);
        return resultModel;
    }
}
