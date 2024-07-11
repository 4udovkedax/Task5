package ru.task5.operations.SettlementAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import ru.task5.entity.TppProductRegister;
import ru.task5.entity.TppRefProductRegisterType;
import ru.task5.model.SettlementAccountModel;
import ru.task5.repositories.TppProductRegisterRepo;
import ru.task5.repositories.TppRefProductRegisterTypeRepo;

import java.util.Optional;

@Component
@Order(100)
public class FoundDoubeProductRegistry implements OperationAccount{
    @Autowired
    TppProductRegisterRepo tppProductRegisterRepo;
    @Autowired
    TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;

    @Override
    public String apply(SettlementAccountModel model, String resultModel) {
        TppProductRegister tppProductRegister = new TppProductRegister();
        TppRefProductRegisterType tppRefProductRegisterType = new TppRefProductRegisterType();

        tppRefProductRegisterType.setValue(model.getRegistryTypeCode());
        Example<TppRefProductRegisterType> example = Example.of(tppRefProductRegisterType);
        Optional<TppRefProductRegisterType> tppProductRegisterTypeOptional = tppRefProductRegisterTypeRepo.findOne(example);

        if (tppProductRegisterTypeOptional.isEmpty()) {
            throw new IllegalArgumentException("Код Продукта <" + model.getRegistryTypeCode() +"> не найдено в Каталоге продуктов <tpp_ref_product_register_type> для данного типа Регистра");
        }

        tppProductRegister.setProductId(model.getInstanceId());
        tppProductRegister.setTppRefProductRegisterType(tppProductRegisterTypeOptional.get());
        Example<TppProductRegister> exampleProduct = Example.of(tppProductRegister);
        Optional<TppProductRegister> tppProductOptional = tppProductRegisterRepo.findOne(exampleProduct);

        if (!tppProductOptional.isEmpty()) {
            throw new IllegalArgumentException("Параметр registryTypeCode тип регистра <" + model.getRegistryTypeCode() + "> уже существует для ЭП с ИД  <" + tppProductOptional.get() + ">");
        }

        model.setTppRefProductRegisterType(tppProductRegisterTypeOptional.get());
        return resultModel;
    }
}
