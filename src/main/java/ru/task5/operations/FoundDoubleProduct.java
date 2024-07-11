package ru.task5.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import ru.task5.entity.TppProduct;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.repositories.TppProductRepo;

import java.util.Optional;

@Component
@Order(100)
public class FoundDoubleProduct implements OperationProduct {
    @Autowired
    TppProductRepo tppProductRepo;

    @Override
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel) {
        TppProduct tppProduct = new TppProduct();
        tppProduct.setNumber(model.getContractNumber());
        Example<TppProduct> example = Example.of(tppProduct);
        Optional<TppProduct> tppProductOptional = tppProductRepo.findOne(example);

        if (!tppProductOptional.isEmpty()) {
            throw new IllegalArgumentException("Параметр ContractNumber № договора <" + model.getContractNumber() + "> уже существует для ЭП с ИД <" + tppProductOptional.get().getId() + ">");
        }

        return resultModel;
    }
}
