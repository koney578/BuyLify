package com.buylify.buylifyapp.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.buylify.buylifyapp.mappers.AddressMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper mapper;

    public void addAddress(CreateAddressDto dto) {
        Address address = mapper.toEntity(dto);
        addressRepository.save(address);
    }

    public List<AddressDto> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(mapper::toAddressDto)
                .toList();
    }

    public AddressDto getAddressById(Long id) {
        return mapper.toAddressDto(addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found")));
    }
}
