package ru.task5.operations.SettlementAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import ru.task5.entity.Account;
import ru.task5.entity.AccountPool;
import ru.task5.entity.TppProductRegister;
import ru.task5.enums.State;
import ru.task5.model.SettlementAccountModel;
import ru.task5.repositories.AccountPoolRepo;
import ru.task5.repositories.AccountRepo;
import ru.task5.repositories.TppProductRegisterRepo;

import java.util.Optional;

@Component
@Order(200)
public class SaveProductRegistry implements OperationAccount{
    @Autowired
    TppProductRegisterRepo tppProductRegisterRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    AccountPoolRepo accountPoolRepo;

    @Override
    public String apply(SettlementAccountModel model, String resultModel) {
        TppProductRegister tppProductRegister = new TppProductRegister();
        Account account;
        AccountPool accountPool = new AccountPool();

        //Найти значение номера счета по параметрам branchCode, currencyCode, mdmCode, priorityCode, registryTypeCode
        accountPool.setBranchCode(model.getBranchCode());
        accountPool.setCurrencyCode(model.getCurrencyCode());
        accountPool.setMdmCode(model.getMdmCode());
        accountPool.setPriorityCode(model.getPriorityCode());
        accountPool.setRegistryTypeCode(model.getRegistryTypeCode());

        Example<AccountPool> example = Example.of(accountPool);
        Optional<AccountPool> accountPoolOptional = accountPoolRepo.findOne(example);
        account = accountRepo.getReferenceById(accountPoolOptional.get().getId());

        tppProductRegister.setProductId(model.getInstanceId());
        tppProductRegister.setTppRefProductRegisterType(model.getTppRefProductRegisterType());
        tppProductRegister.setAccount(account.getId());
        tppProductRegister.setCurrencyCode(model.getCurrencyCode());
        tppProductRegister.setState(State.Открыт.name());
        tppProductRegister.setAccountNumber(account.getAccountNumber());

        tppProductRegisterRepo.save(tppProductRegister);
        return String.valueOf(tppProductRegister.getId());
    }
}
