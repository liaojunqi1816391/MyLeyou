package com.leyou.address.client;

import com.leyou.item.api.AddressApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface AddressClient extends AddressApi {
}
