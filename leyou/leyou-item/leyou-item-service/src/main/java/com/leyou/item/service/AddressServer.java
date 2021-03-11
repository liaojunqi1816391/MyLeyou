package com.leyou.item.service;

import com.leyou.item.pojo.Address;

import java.util.List;

public interface AddressServer {
    List<Address> getAddressByUserId(Long userId);

    void addAddress(Address address);
}
