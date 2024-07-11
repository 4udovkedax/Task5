package ru.task5.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.task5.entity.TppRefProductRegisterType;

@Getter
@Setter
public class SettlementAccountModel {
    @NotNull(message = "\"Идентификатор экземпляра продукта\" не заполнен")
    private Long instanceId;
    private String registryTypeCode;
    private String accountType;
    private String currencyCode;
    private String branchCode;
    private String priorityCode;
    private String mdmCode;
    private String clientCode;
    private String trainRegion;
    private String counter;
    private String salesCode;

    private TppRefProductRegisterType tppRefProductRegisterType;
}
