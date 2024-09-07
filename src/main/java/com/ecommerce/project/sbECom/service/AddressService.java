package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.model.User;
import com.ecommerce.project.sbECom.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long addressId);
}
