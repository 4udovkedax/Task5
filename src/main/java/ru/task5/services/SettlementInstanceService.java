package ru.task5.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.operations.OperationAgreement;
import ru.task5.operations.OperationProduct;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementInstanceService {
    private List<OperationProduct> operProducts = new ArrayList<>();
    private List<OperationAgreement> operAgreements = new ArrayList<>();
    @Getter
    @Setter
    private ResultModel resultModel = new ResultModel();

    public SettlementInstanceService(List<OperationProduct> operProducts, List<OperationAgreement> operAgreements) {
        this.operProducts = operProducts;
        this.operAgreements = operAgreements;
    }

    @Transactional
    public void startOperations(SettlementInstanceModel model) {
        if (model.getInstanceId() == null) {
            for (OperationProduct o : operProducts) {
                resultModel = o.apply(model, resultModel);
            }
        } else {
            for (OperationAgreement o : operAgreements) {
                resultModel = o.apply(model, resultModel);
            }
        }
    }
}
