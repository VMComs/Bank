package com.bank.authorization.entity;

/**
 * Перечисление возможных ролей для сущности Role
 */

public enum  RoleEnum {
    ROLE_ADMIN,
    ROLE_USER

//    public static RoleEnum find(String role) {
//        return findOpt(role).orElseThrow();
//    }
//
//    public static Optional<RoleEnum> findOpt(String role) {
//        return Arrays.stream(values())
//                .filter(it -> it.name().equals(role))
//                .findFirst();
//    }
}
