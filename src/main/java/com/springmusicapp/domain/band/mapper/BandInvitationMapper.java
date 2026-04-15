package com.springmusicapp.domain.band.mapper;

import com.springmusicapp.domain.band.dto.BandInvitationDTO;
import com.springmusicapp.domain.band.model.BandInvitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BandInvitationMapper {

    @Mapping(source = "id", target = "invitationId")
    @Mapping(source = "band.id", target = "bandId")
    @Mapping(source = "band.name", target = "bandName")
    BandInvitationDTO toDto(BandInvitation invitation);
}