package ru.task5.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.services.SettlementInstanceService;

@RestController
public class SettlementInstanceController {
    @Autowired
    private SettlementInstanceService settlementInstanceService;

    @PostMapping("corporate-settlement-instance/create")
    public ResponseEntity<ResultModel> handle(@Valid @RequestBody SettlementInstanceModel model) {
        settlementInstanceService.startOperations(model);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(settlementInstanceService.getResultModel());
    }
}
