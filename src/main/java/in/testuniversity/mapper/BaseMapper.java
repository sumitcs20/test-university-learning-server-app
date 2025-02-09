package in.testuniversity.mapper;

import in.testuniversity.exception.MappingException;

public interface BaseMapper<E, D> {
	E dtoToEntity(D dto) throws MappingException;
    D entityToDTO(E entity) throws MappingException;
}
