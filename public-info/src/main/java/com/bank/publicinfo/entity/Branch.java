package com.bank.publicinfo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;

/**
 * Сущность Branch - отделение банка.
 * <b>address</b> - адрес отделения. Обязательное поле
 * <b>phoneNumber</b> - номер телефона отделения. Обязательное поле
 * <b>city</b> - город расположения отделения. Обязательное поле
 * <b>startOfWork</b> - время начала работы отделения. Обязательное поле
 * <b>endOfWork</b> - время окончания работы отделения. Обязательное поле
 *
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "branch", schema = "public_bank_information")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    @Size(max = 370)
    @Column(name = "address")
    private String address;

    @NonNull
    @NotNull
    @Column(name = "phone_number")
    private Long phoneNumber;

    @NonNull
    @NotEmpty
    @Size(max = 250)
    @Column(name = "city")
    private String city;

    @NonNull
    @NotNull
    @Column(name = "start_of_work")
    private Time startOfWork;

    @NonNull
    @NotNull
    @Column(name = "end_of_work")
    private Time endOfWork;
}
