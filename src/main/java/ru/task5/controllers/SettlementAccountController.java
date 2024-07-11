package ru.task5.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.task5.model.ResModel;
import ru.task5.model.SettlementAccountModel;
import ru.task5.services.SettlementAccountService;

@RestController
public class SettlementAccountController {
    @Autowired
    SettlementAccountService settlementAccountService;

    @PostMapping("corporate-settlement-account/create")
    public ResponseEntity<ResModel> handle(@Valid @RequestBody SettlementAccountModel model) {
        settlementAccountService.startOperations(model);

        ResModel resModel = new ResModel();
        resModel.setInstanceId(settlementAccountService.getResultModel());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resModel);
    }
}
