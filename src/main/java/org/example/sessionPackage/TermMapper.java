package org.example.sessionPackage;

import org.example.data.model.Term;
import org.example.sessionPackage.dto.sessionResponse.SessionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface TermMapper {

    TermMapper INSTANCE = Mappers.getMapper(TermMapper.class);
//    private final SessionMapper sessionMapper;


    @Mapping(source = "adminTenant.schoolName", target = "schoolName")
    @Mapping(source = "adminTenant.subdomain", target = "subdomain")
    @Mapping(source = "term", target = "term", qualifiedByName = "termToString")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "formatDate")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "formatDate")
    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    SessionDTO toDTO(Term term);

//    @Named("formatDate")
//    default String formatDate(LocalDate date) {
//        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
//    }
    @Named("formatDate")
    default String formatDate(Date date) {
        if (date == null) return null;
        return java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .format(date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
    }

    @Named("termToString")
    default String termToString(Enum<?> term) {
        return term != null ? term.name() : null;
    }

    @Named("statusToString")
    default String statusToString(Enum<?> status) {
        return status != null ? status.name() : null;
    }
}
