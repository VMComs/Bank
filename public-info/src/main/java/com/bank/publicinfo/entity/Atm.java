package com.bank.publicinfo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;

/**
 * Сущность Atm - банкомат.
 * <b>address</b> - адрес банкомата, например, "ул. Пупкина, д.2". Обязательное поле
 * <b>startOfWork</b> - время начала работы банкомата, например, "4:00". Может быть пустым
 * <b>endOfWork</b> - время окончания работы банкомата, например, "00:00". Может быть пустым
 * <b>allHours</b> - является ли банкомат круглосуточным. Обязательное поле
 * <b>branch</b> - отделение банка, где расположен банкомат. Внешний ключ, ссылается
 * на таблицу <b>branch</b>. Может быть пустым
 *
 * @see Branch
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "atm", schema = "public_bank_information")
public class Atm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    @Size(max = 370)
    @Column(name = "address")
    private String address;

    @Column(name = "start_of_work")
    private Time startOfWork;

    @Column(name = "end_of_work")
    private Time endOfWork;

    @NonNull
    @NotNull
    @Column(name = "all_hours")
    private Boolean allHours;

    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;
}
