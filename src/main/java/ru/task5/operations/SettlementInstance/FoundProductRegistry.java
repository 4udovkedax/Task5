package ru.task5.operations.SettlementInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import ru.task5.entity.TppRefAccountType;
import ru.task5.entity.TppRefProductClass;
import ru.task5.entity.TppRefProductRegisterType;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.repositories.TppRefAccountTypeRepo;
import ru.task5.repositories.TppRefProductClassRepo;
import ru.task5.repositories.TppRefProductRegisterTypeRepo;

import java.util.Optional;

@Component
@Order(300)
public class FoundProductRegistry implements OperationProduct{
    @Autowired
    TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;
    @Autowired
    TppRefProductClassRepo tppRefProductClassRepo;
    @Autowired
    TppRefAccountTypeRepo tppRefAccountTypeRepo;

    @Override
    public ResultModel apply(SettlementInstanceModel model, ResultModel resultModel) {
        TppRefProductClass tppRefProductClass = new TppRefProductClass();
        TppRefProductRegisterType tppRefProductRegisterType = new TppRefProductRegisterType();
        TppRefAccountType tppRefAccountType = new TppRefAccountType();


        tppRefProductClass.setValue(model.getProductCode());
        Example<TppRefProductClass> example = Example.of(tppRefProductClass);
        Optional<TppRefProductClass> tppProductOptional = tppRefProductClassRepo.findOne(example);

        if (tppProductOptional.isEmpty()) {
            throw new NullPointerException("КодПродукта <" + model.getProductCode() +"> не найден в Каталоге продуктов tpp_ref_product_class");
        }

        tppRefAccountType.setValue("Клиентский");
        Example<TppRefAccountType> exampleAcc = Example.of(tppRefAccountType);
        Optional<TppRefAccountType> tppRefAccountTypeOptional = tppRefAccountTypeRepo.findOne(exampleAcc);

        tppRefProductRegisterType.setTppRefProductClass(tppProductOptional.get());
        tppRefProductRegisterType.setTppRefAccountType(tppRefAccountTypeOptional.get());
        Example<TppRefProductRegisterType> exampleType = Example.of(tppRefProductRegisterType);
        model.setRefProductRegisterTypes(tppRefProductRegisterTypeRepo.findAll(exampleType));

        return resultModel;
    }
}
