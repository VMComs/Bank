package com.bank.publicinfo.util;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурации JsonConfiguration для настройки парсинга JSON.
 * Hibernate5Module - дополнительный модуль для Jackson JSON. Обрабатывает типы данных Hibernate
 * и особенно аспекты ленивой загрузки
 *
 */

@Configuration
public class JsonConfiguration {
    @Bean
    public Module hibernateModule() {
        return new Hibernate5Module();
    }
}