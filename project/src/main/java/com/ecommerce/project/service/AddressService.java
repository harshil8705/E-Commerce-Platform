package com.ecommerce.project.service;

import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO, User loggedInUser);

    List<AddressDTO> getAllAddress();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getAddressByUser(User loggedInUser);

    AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO);

    String deleteAddressById(Long addressId);

}
