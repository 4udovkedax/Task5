package ru.task5.operations.SettlementInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.task5.entity.Agreement;
import ru.task5.model.InstanceArrangement;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.repositories.AgreementRepo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(600)
public class SaveAgreement implements OperationAgreement{
    @Autowired
    AgreementRepo agreementRepo;

    @Override
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel) {
        List<String> agrArr = new ArrayList<>();
        for (InstanceArrangement agr: model.getInstanceArrangement()) {
            Agreement agreement = new Agreement();
            agreement.setProductId(model.getInstanceId());
            agreement.setGeneralAgreementId(agr.getGeneralAgreementId());
            agreement.setSupplementaryAgreementId(agr.getSupplementaryAgreementId());
            agreement.setArrangementType(((agr.getArrangementType() == null) ? null : agr.getArrangementType().name()));
            agreement.setShedulerJobId(agr.getShedulerJobId());
            agreement.setNumber(agr.getNumber());
            agreement.setOpeningDate(((agr.getOpeningDate() == null) ? null : new Timestamp(agr.getOpeningDate().getTime())));
            agreement.setClosingDate(((agr.getClosingDate() == null) ? null : new Timestamp(agr.getClosingDate().getTime())));
            agreement.setCancelDate(((agr.getCancelDate() == null) ? null : new Timestamp(agr.getCancelDate().getTime())));
            agreement.setValidityDuration(agr.getValidityDuration());
            agreement.setCancellationReason(agr.getCancellationReason());
            agreement.setStatus(agr.getStatus());
            agreement.setInterestCalculationDate(((agr.getInterestCalculationDate() == null) ? null : new Timestamp(agr.getInterestCalculationDate().getTime())));
            agreement.setInterestRate(agr.getInterestRate());
            agreement.setCoefficient(agr.getCoefficient());
            agreement.setCoefficientAction(((agr.getCoefficientAction() == null) ? null : agr.getCoefficientAction().name()));
            agreement.setMinimumInterestRate(agr.getMinimumInterestRate());
            agreement.setMinimumInterestRateCoefficient(agr.getMinimumInterestRateCoefficient());
            agreement.setMinimumInterestRateCoefficientAction(((agr.getMinimumInterestRateCoefficientAction() == null) ? null : agr.getMinimumInterestRateCoefficientAction().name()));
            agreement.setMaximalInterestRate(agr.getMaximalnterestRate());
            agreement.setMaximalInterestRateCoefficient(agr.getMaximalnterestRateCoefficient());
            agreement.setMaximalInterestRateCoefficientAction(((agr.getMaximalnterestRateCoefficientAction() == null) ? null : agr.getMaximalnterestRateCoefficientAction().name()));

            agreementRepo.save(agreement);
            agrArr.add(String.valueOf(agreement.getId()));
        }

        resultModel.setSupplementaryAgreementId(agrArr);
        return resultModel;
    }
}
