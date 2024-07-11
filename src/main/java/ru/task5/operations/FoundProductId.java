package ru.task5.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.task5.entity.TppProduct;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.repositories.TppProductRepo;

import java.util.Optional;

@Component
@Order(150)
public class FoundProductId implements OperationAgreement{
    @Autowired
    TppProductRepo tppProductRepo;

    @Override
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel) {
        Optional<TppProduct> tppProductOptional = tppProductRepo.findById(model.getInstanceId());

        if (tppProductOptional.isEmpty()) {
            throw new NullPointerException("Экземпляр продукта с параметром instanceId <" + model.getInstanceId() + "> не найден");
        }
        resultModel.setInstanceId(String.valueOf(model.getInstanceId()));
        return resultModel;
    }
}
