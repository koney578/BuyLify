package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.opinion.Opinion;
import com.buylify.buylifyapp.opinion.OpinionDto;
import org.mapstruct.Mapper;

@Mapper
public interface OpinionMapper {

    OpinionDto toDto(Opinion opinion);
}
