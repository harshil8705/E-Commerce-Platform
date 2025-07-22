package com.ecommerce.project.controller;

import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {

        User loggedInUser = authUtil.loggedInUser();

        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, loggedInUser);

        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);

    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddress() {

        List<AddressDTO> addressDTOList = addressService.getAllAddress();

        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);

    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {

        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);

    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressByUser() {

        User loggedInUser = authUtil.loggedInUser();

        return new ResponseEntity<>(addressService.getAddressByUser(loggedInUser), HttpStatus.OK);

    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressById(@PathVariable Long addressId,
                                                        @RequestBody AddressDTO addressDTO) {

        return new ResponseEntity<>(addressService.updateAddressById(addressId, addressDTO), HttpStatus.OK);

    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId) {

        return new ResponseEntity<>(addressService.deleteAddressById(addressId), HttpStatus.OK);

    }

}
