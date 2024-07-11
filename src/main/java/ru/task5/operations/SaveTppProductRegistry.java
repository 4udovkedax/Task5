package ru.task5.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.task5.entity.TppProductRegister;
import ru.task5.entity.TppRefProductRegisterType;
import ru.task5.enums.State;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.repositories.TppProductRegisterRepo;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(500)
public class SaveTppProductRegistry implements OperationProduct{
    @Autowired
    TppProductRegisterRepo tppProductRegisterRepo;

    @Override
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel) {
        List<String> registerId = new ArrayList<>();

        for (TppRefProductRegisterType regTyp: model.getRefProductRegisterTypes()) {
            TppProductRegister tppProductRegister = new TppProductRegister();
            tppProductRegister.setProductId(model.getInstanceId());
            tppProductRegister.setTppRefProductRegisterType(regTyp);
            tppProductRegister.setState(State.Открыт.name());
            tppProductRegister.setCurrencyCode(model.getIsoCurrencyCode());
//            tppProductRegister.setAccount(account.getId());
//            tppProductRegister.setAccountNumber(account.getAccountNumber());

            tppProductRegisterRepo.save(tppProductRegister);
            registerId.add(String.valueOf(tppProductRegister.getId()));
        }

        resultModel.setRegisterId(registerId);
        return resultModel;
    }
}
