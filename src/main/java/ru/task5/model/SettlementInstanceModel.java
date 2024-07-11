package ru.task5.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.task5.entity.TppRefProductRegisterType;
import ru.task5.enums.ProductType;
import ru.task5.enums.RateType;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class SettlementInstanceModel {
    private Long instanceId;

    @NotNull(message = "\"Тип Экземпляра Продукта\" не заполнен")
    private ProductType productType;

    @NotNull(message = "\"Код продукта в каталоге продуктов\" не заполнен")
    private String productCode;

    @NotNull(message = "\"Тип регистра\" не заполнен")
    private String registerType;

    @NotNull(message = "\"Код Клиента (mdm)\" не заполнен")
    private String mdmCode;

    @NotNull(message = "\"Номер договора\" не заполнен")
    private String contractNumber;

    @NotNull(message = "\"Дата заключения договора обслуживания\" не заполнена")
    private Date contractDate;

    @NotNull(message = "\"Приоритет\" не заполнен")
    private Integer priority;

    private BigDecimal interestRatePenalty;

    private BigDecimal minimalBalance;

    private BigDecimal thresholdAmount;

    private String accountingDetails;

    private RateType rateType;

    private BigDecimal taxPercentageRate;

    private BigDecimal technicalOverdraftLimitAmount;

    @NotNull(message = "\"ID Договора \" не заполнен")
    private Long contractId;

    @NotNull(message = "\"Код филиала\" не заполнен")
    private String branchCode;

    @NotNull(message = "\"Код валюты\" не заполнен")
    private String isoCurrencyCode;

    @NotNull(message = "\"Код срочности договора\" не заполнен")
    private String urgencyCode;

    private Integer referenceCode;

    private AdditionalProperties additionalPropertiesVip;

    @Valid
    private List<InstanceArrangement> instanceArrangement = new ArrayList<>();

    private List<TppRefProductRegisterType> refProductRegisterTypes = new ArrayList<>();
}
