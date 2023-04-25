package com.bank.history.entity.dto;

import com.bank.history.entity.History;
import org.mapstruct.Mapper;

/**
 * переводит ентити в дто и обратно, будут лежать в
 * ${projectDir}/target/generated-sources/annotation/com/bank/history/entity/dto/HistoryMapperImpl
 * одинаковые, поэтому просто переходят
 */

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    /**
     *
     * @param history
     * @return dto
     */
    HistoryDTO toDTO(History history);

    /**
     *
     * @param historyDTO
     * @return history
     */
    History toEntity(HistoryDTO historyDTO);
}
