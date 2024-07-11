package ru.task5.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.task5.enums.CoefficientAction;
import ru.task5.enums.ProductType;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class InstanceArrangement {
    private String generalAgreementId;

    private String supplementaryAgreementId;

    private ProductType arrangementType;

    private BigDecimal shedulerJobId;

    @NotNull(message = "\"Номер ДС\" не заполнено")
    private String number;

    @NotNull(message = "\"Дата начала сделки\" не заполнена")
    private Date openingDate;

    private Date closingDate;

    private Date cancelDate;

    private BigDecimal validityDuration;

    private String cancellationReason;

    private String status;

    private Date interestCalculationDate;

    private BigDecimal interestRate;

    private BigDecimal coefficient;

    private CoefficientAction coefficientAction;

    private BigDecimal minimumInterestRate;

    private BigDecimal minimumInterestRateCoefficient;

    private CoefficientAction minimumInterestRateCoefficientAction;

    private BigDecimal maximalnterestRate;

    private BigDecimal maximalnterestRateCoefficient;

    private CoefficientAction maximalnterestRateCoefficientAction;
}
