package ru.task5.controllers;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.task5.model.SettlementInstanceModel;

@RestController
public class SettlementInstanceController {
    @PostMapping("corporate-settlement-instance/create")
    public ResponseEntity<SettlementInstanceModel> handle(@Valid @RequestBody SettlementInstanceModel model) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(model);
    }
}
