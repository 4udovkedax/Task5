package ru.task5;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.task5.controllers.SettlementInstanceController;
import ru.task5.entity.TppProduct;
import ru.task5.entity.TppRefAccountType;
import ru.task5.entity.TppRefProductRegisterType;
import ru.task5.enums.RateType;
import ru.task5.model.ResultModel;
import ru.task5.model.SettlementAccountModel;
import ru.task5.model.SettlementInstanceModel;
import ru.task5.operations.SettlementAccount.FoundDoubeProductRegistry;
import ru.task5.operations.SettlementInstance.FoundDoubleProduct;
import ru.task5.operations.SettlementInstance.FoundProductId;
import ru.task5.operations.SettlementInstance.FoundProductRegistry;
import ru.task5.operations.SettlementInstance.SaveTppProduct;
import ru.task5.repositories.TppProductRepo;

import java.math.BigDecimal;
import java.util.*;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestOperations {
    private static ApplicationContext context;
    @LocalServerPort
    private Integer port;

    {
        context = SpringApplication.run(Main.class);
    }

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Sql(scripts = "Script.sql")

    @Test
    @DisplayName("Проверка заполнения полей в запросе")
    public void testFieldsNotNull() {
        SettlementInstanceModel settlementInstanceModel = new SettlementInstanceModel();
        Validator validator = context.getBean(Validator.class);
        int count = 0;

        for (ConstraintViolation<SettlementInstanceModel> model : validator.validate(settlementInstanceModel)) {
            if (model.getMessage().equals("\"Код филиала\" не заполнен")
                    || model.getMessage().equals("\"Тип Экземпляра Продукта\" не заполнен")
                    || model.getMessage().equals("\"Код валюты\" не заполнен")
                    || model.getMessage().equals("\"Дата заключения договора обслуживания\" не заполнена")
                    || model.getMessage().equals("\"Тип регистра\" не заполнен")
                    || model.getMessage().equals("\"Код продукта в каталоге продуктов\" не заполнен")
                    || model.getMessage().equals("\"ID Договора \" не заполнен")
                    || model.getMessage().equals("\"Номер договора\" не заполнен")
                    || model.getMessage().equals("\"Код Клиента (mdm)\" не заполнен")
                    || model.getMessage().equals("\"Код срочности договора\" не заполнен")
                    || model.getMessage().equals("\"Тип Экземпляра Продукта\" не заполнен")
                    || model.getMessage().equals("\"Приоритет\" не заполнен")
            ) {
                count++;
                continue;
            }
            Assertions.assertEquals(model.getMessage(), "Печалька");
        }
        Assertions.assertEquals(count, 11);
    }

    @Test
    @DisplayName("Проверка заполнения полей в запросе по счетам")
    public void testFieldsNotNullAccount() {
        SettlementAccountModel settlementAccountModel = new SettlementAccountModel();
        Validator validator = context.getBean(Validator.class);
        int count = 0;

        for (ConstraintViolation<SettlementAccountModel> model : validator.validate(settlementAccountModel)) {
            if (model.getMessage().equals("\"Идентификатор экземпляра продукта\" не заполнен")) {
                count++;
                continue;
            }
            Assertions.assertEquals(model.getMessage(), "Печалька");
        }
        Assertions.assertEquals(count, 1);
    }

    @Test
    @DisplayName("Проверка операции FoundDoubleProduct")
    public void checkFoundDoubleProduct() {
        TppProductRepo tppProductRepo = context.getBean(TppProductRepo.class);
        FoundDoubleProduct oper = context.getBean(FoundDoubleProduct.class);
        SettlementInstanceModel settlementInstanceModel = new SettlementInstanceModel();
        ResultModel resultOld = new ResultModel();
        ResultModel resultNew = new ResultModel();
        TppProduct tppProduct = new TppProduct();

        settlementInstanceModel.setContractNumber("111");
        resultNew = oper.apply(settlementInstanceModel, resultOld);
        Assertions.assertEquals(resultNew, resultOld);

        tppProduct.setNumber("111");
        tppProductRepo.save(tppProduct);
        Assertions.assertThrows(IllegalArgumentException.class, ()->oper.apply(settlementInstanceModel, resultOld));
    }

    @Test
    @DisplayName("Проверка операции FoundProductId")
    public void checkFoundProductId() {
        TppProductRepo tppProductRepo = context.getBean(TppProductRepo.class);
        FoundProductId oper = context.getBean(FoundProductId.class);
        SettlementInstanceModel settlementInstanceModel = new SettlementInstanceModel();
        ResultModel resultOld = new ResultModel();
        TppProduct tppProduct = new TppProduct();

        tppProduct.setNumber("222");
        tppProductRepo.save(tppProduct);
        settlementInstanceModel.setInstanceId(tppProduct.getId()+100);
        Assertions.assertThrows(NullPointerException.class, ()->oper.apply(settlementInstanceModel, resultOld));

        settlementInstanceModel.setInstanceId(tppProduct.getId());
        Assertions.assertEquals(resultOld, oper.apply(settlementInstanceModel, resultOld));
    }

    @Test
    @DisplayName("Проверка операции FoundProductRegistry")
    public void checkFoundProductRegistry() {
        FoundProductRegistry oper = context.getBean(FoundProductRegistry.class);
        SettlementInstanceModel settlementInstanceModel = new SettlementInstanceModel();
        ResultModel resultOld = new ResultModel();

        settlementInstanceModel.setProductCode("123123123");
        Assertions.assertThrows(NullPointerException.class, ()->oper.apply(settlementInstanceModel, resultOld));

        settlementInstanceModel.setProductCode("03.012.002");
        Assertions.assertEquals(resultOld, oper.apply(settlementInstanceModel, resultOld));
    }

    @Test
    @DisplayName("Проверка операции SaveTppProduct")
    public void checkSaveTppProduct() {
        SaveTppProduct oper = context.getBean(SaveTppProduct.class);
        SettlementInstanceModel settlementInstanceModel = new SettlementInstanceModel();
        TppRefProductRegisterType tppRefProductRegisterType = new TppRefProductRegisterType();
        TppRefAccountType tppRefAccountType = new TppRefAccountType();
        ResultModel resultOld = new ResultModel();
        ResultModel resultNew = new ResultModel();

        tppRefAccountType.setId(1L);
        tppRefProductRegisterType.setId(1L);
        tppRefProductRegisterType.setTppRefAccountType(tppRefAccountType);
        List<TppRefProductRegisterType> list = new ArrayList<>();
        list.add(tppRefProductRegisterType);

        settlementInstanceModel.setContractNumber("123");
        settlementInstanceModel.setPriority(1);
        settlementInstanceModel.setRateType(RateType.дифференцированная);
        settlementInstanceModel.setContractDate(new Date());
        settlementInstanceModel.setTaxPercentageRate(BigDecimal.valueOf(123));
        settlementInstanceModel.setThresholdAmount(BigDecimal.valueOf(123));
        settlementInstanceModel.setThresholdAmount(BigDecimal.valueOf(123));
        settlementInstanceModel.setRefProductRegisterTypes(list);

        resultNew = oper.apply(settlementInstanceModel, resultOld);

        Assertions.assertEquals(false, resultNew.getInstanceId().isEmpty());
    }

    @Test
    @DisplayName("Проверка операции FoundDoubeProductRegistry")
    public void checkFoundDoubeProductRegistry() {
        FoundDoubeProductRegistry oper = context.getBean(FoundDoubeProductRegistry.class);
        SettlementAccountModel settlementAccountModel = new SettlementAccountModel();
        String resultOld = "";
        String resultNew = "";

        settlementAccountModel.setRegistryTypeCode("123123123");
        Assertions.assertThrows(IllegalArgumentException.class, ()->oper.apply(settlementAccountModel, resultOld));

        settlementAccountModel.setRegistryTypeCode("03.012.002_47533_ComSoLd");
        settlementAccountModel.setInstanceId(333L);
        resultNew = oper.apply(settlementAccountModel, resultOld);
        Assertions.assertEquals(resultNew, resultOld);
    }

    @Test
    @DisplayName("Проверка операции SettlementInstanceController по запросу")
    public void testSettlementInstanceController() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("productType", "НСО");
        request.put("productCode", "03.012.002");
        request.put("registerType", "03.012.002_47533_ComSoLd");
        request.put("mdmCode", "15");
        request.put("contractNumber", "123");
        request.put("contractDate", "2024-01-25");
        request.put("priority", 1);
        request.put("rateType", 1);
        request.put("contractId", "123");
        request.put("branchCode", "0022");
        request.put("isoCurrencyCode", "800");
        request.put("urgencyCode", "стандартно");

        Response response = given()
                .contentType(ContentType.JSON)
                .with()
                .body(request)
                .when()
                .post("/corporate-settlement-instance/create")
                .then()
                .statusCode(200)
                .extract().response()
        ;
        System.out.println(response.body().print());

    }

    @Test
    @DisplayName("Проверка операции SettlementInstanceController по запросу с ошибкой")
    public void testSettlementInstanceControllerErr() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("productType", "НСО");
        request.put("productCode", "error");
        request.put("registerType", "error");
        request.put("mdmCode", "1512");
        request.put("contractNumber", "123");
        request.put("contractDate", "2024-01-25");
        request.put("priority", 1);
        request.put("rateType", 1);
        request.put("contractId", "123");
        request.put("branchCode", "0022");
        request.put("isoCurrencyCode", "800");
        request.put("urgencyCode", "стандартно");

        Response response = given()
                .contentType(ContentType.JSON)
                .with()
                .body(request)
                .when()
                .post("/corporate-settlement-instance/create")
                .then()
                .statusCode(404)
                .extract().response()
                ;
        System.out.println(response.body().print());

    }

    @Test
    @DisplayName("Проверка операции SettlementAccountController по запросу")
    public void SettlementAccountController() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("instanceId", 1);
        request.put("registryTypeCode", "03.012.002_47533_ComSoLd");
        request.put("accountType", null);
        request.put("currencyCode", "800");
        request.put("branchCode", "0022");
        request.put("priorityCode", "00");
        request.put("mdmCode", "15");

        Response response = given()
                .contentType(ContentType.JSON)
                .with()
                .body(request)
                .when()
                .post("corporate-settlement-account/create")
                .then()
                .statusCode(200)
                .extract().response()
                ;
        System.out.println(response.body().print());

    }

    @Test
    @DisplayName("Проверка операции SettlementAccountController по запросу с ошибокой")
    public void SettlementAccountControllerErr() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("instanceId", 1);
        request.put("registryTypeCode", "error");
        request.put("accountType", null);
        request.put("currencyCode", "123800");
        request.put("branchCode", "qwe");
        request.put("priorityCode", "00");
        request.put("mdmCode", "15");

        Response response = given()
                .contentType(ContentType.JSON)
                .with()
                .body(request)
                .when()
                .post("corporate-settlement-account/create")
                .then()
                .statusCode(400)
                .extract().response()
                ;
        System.out.println(response.body().print());

    }
 }
