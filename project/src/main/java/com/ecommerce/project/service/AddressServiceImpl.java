package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User loggedInUser) {

        Address address = modelMapper.map(addressDTO, Address.class);

        List<Address> addressList = loggedInUser.getAddresses();
        addressList.add(address);
        loggedInUser.setAddresses(addressList);

        address.setUser(loggedInUser);

        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);

    }

    @Override
    public List<AddressDTO> getAllAddress() {

        List<Address> addressList = addressRepository.findAll();

        return addressList.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();

    }

    @Override
    public AddressDTO getAddressById(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        return modelMapper.map(address, AddressDTO.class);

    }

    @Override
    public List<AddressDTO> getAddressByUser(User loggedInUser) {

        List<Address> addressList = loggedInUser.getAddresses();

        return addressList.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();

    }

    @Override
    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        Address convertedDTOAddress = modelMapper.map(addressDTO, Address.class);

        address.setCity(convertedDTOAddress.getCity());
        address.setState(convertedDTOAddress.getState());
        address.setPincode(convertedDTOAddress.getPincode());
        address.setCountry(convertedDTOAddress.getCountry());
        address.setStreet(convertedDTOAddress.getStreet());
        address.setBuildingName(convertedDTOAddress.getBuildingName());

        Address updatedAddress = addressRepository.save(address);

        User user = address.getUser();

        user.getAddresses().removeIf(address1 -> address1.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);

        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);

    }

    @Override
    public String deleteAddressById(Long addressId) {

        Address addressToBeDeleted = addressRepository.findById(addressId)
                        .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        User user = addressToBeDeleted.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressToBeDeleted);

        return "Address Deleted Successfully with addressId : " + addressId + " !!";

    }

}
