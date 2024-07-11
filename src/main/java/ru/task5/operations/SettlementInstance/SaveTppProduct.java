package ru.task5.operations.SettlementInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.task5.entity.TppProduct;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.repositories.TppProductRepo;

import java.sql.Timestamp;

@Component
@Order(400)
public class SaveTppProduct implements OperationProduct{
    @Autowired
    TppProductRepo tppProductRepo;

    @Override
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel) {
        TppProduct tppProduct = new TppProduct();

        tppProduct.setProductCodeId(model.getRefProductRegisterTypes().get(0).getTppRefAccountType().getId().longValue());
        tppProduct.setNumber(model.getContractNumber());
        tppProduct.setPriority(model.getPriority().longValue());
        tppProduct.setDateOfConclusion(new Timestamp(model.getContractDate().getTime()));
        tppProduct.setInterestRateType(((model.getRateType() == null) ? null : model.getRateType().name()));
        tppProduct.setTaxRate(model.getTaxPercentageRate());
        tppProduct.setThresholdAmount(model.getThresholdAmount());
        tppProduct.setNso(model.getMinimalBalance());
        tppProductRepo.save(tppProduct);

        model.setInstanceId(tppProduct.getId());
        resultModel.setInstanceId(String.valueOf(tppProduct.getId()));
        return resultModel;
    }
}
