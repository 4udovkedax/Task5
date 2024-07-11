package ru.task5.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.*;

@Entity
@Table(name = "tpp_ref_product_register_type")
@Getter
@Setter
public class TppRefProductRegisterType {
    @Id
    @Column(name = "internal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "register_type_name")
    private String registerTypeName;

    @Column(name = "register_type_start_date")
    private Timestamp registerTypeStartDate;

    @Column(name = "register_type_end_date")
    private Timestamp registerTypeEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_class_code",
            referencedColumnName = "value",
            nullable = false
    )
    private TppRefProductClass tppRefProductClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "account_type",
            referencedColumnName = "value"
    )
    private TppRefAccountType tppRefAccountType;
}
