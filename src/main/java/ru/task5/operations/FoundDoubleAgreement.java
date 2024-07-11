package ru.task5.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import ru.task5.entity.Agreement;
import ru.task5.model.InstanceArrangement;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.repositories.AgreementRepo;

import java.util.Optional;

@Component
@Order(200)
public class FoundDoubleAgreement implements OperationProduct, OperationAgreement {
    @Autowired
    AgreementRepo agreementRepo;

    @Override
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel) {
        Agreement agreement = new Agreement();

        for (InstanceArrangement agr : model.getInstanceArrangement()) {
            agreement.setNumber(agr.getNumber());
            Example<Agreement> example = Example.of(agreement);
            Optional<Agreement> agreementOptional = agreementRepo.findOne(example);

            if (!agreementOptional.isEmpty()) {
                throw new IllegalArgumentException("Параметр ContractNumber № договора <" + agr.getNumber() + "> уже существует для ЭП с ИД <" + agreementOptional.get().getId() + ">");
            }
        }

        return resultModel;
    }
}
