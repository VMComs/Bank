package com.bank.account.model.mapper;


/**
 * Абстрактный класс для конвертации в DTO и обратно
 * Зокоментированы неиспользуемые методы, которые могут в дальнейшем пригодиться. Можно удалить, чтобы не было warnings
 */

public abstract class BaseMapper<E, D> {
    public abstract E convertToEntity(D dto, Object... args);

    public abstract D convertToDto(E entity, Object... args);

//    public Collection<E> convertToEntity(Collection<D> dto, Object... args) {
//        return dto.stream().map(d -> convertToEntity(d, args)).collect(Collectors.toList());
//    }
//
//    public Collection<D> convertToDto(Collection<E> entity, Object... args) {
//        return entity.stream().map(e -> convertToDto(e, args)).collect(Collectors.toList());
//    }
//
//    public List<E> convertToEntityList(Collection<D> dto, Object... args) {
//        return new ArrayList<>(convertToEntity(dto, args));
//    }
//
//    public List<D> convertToDtoList(Collection<E> entity, Object... args) {
//        return new ArrayList<>(convertToDto(entity, args));
//    }
//
//    public Set<E> convertToEntitySet(Collection<D> dto, Object... args) {
//        return new HashSet<>(convertToEntity(dto, args));
//    }
//
//    public Set<D> convertToDtoSet(Collection<E> entity, Object... args) {
//        return new HashSet<>(convertToDto(entity, args));
//    }
}