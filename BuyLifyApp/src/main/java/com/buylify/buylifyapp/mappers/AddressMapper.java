package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.address.CreateAddressDto;
import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.address.AddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    Address toEntity(CreateAddressDto dto);

    AddressDto toAddressDto(Address address);
}
