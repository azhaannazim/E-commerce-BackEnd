package com.ecommerce.project.sbECom.controller;

import com.ecommerce.project.sbECom.model.User;
import com.ecommerce.project.sbECom.payload.AddressDTO;
import com.ecommerce.project.sbECom.service.AddressService;
import com.ecommerce.project.sbECom.util.AuthUtil;
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
    AddressService addressService;
    @Autowired
    AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO , user);
        return new ResponseEntity<>(savedAddressDTO , HttpStatus.CREATED);
    }
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        return new ResponseEntity<>(addressService.getAllAddresses() , HttpStatus.FOUND);
    }
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAllAddresses(@Valid @PathVariable Long addressId){
        return new ResponseEntity<>(addressService.getAddressById(addressId) , HttpStatus.FOUND);
    }
}
