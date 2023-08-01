package com.lobemus.shoppingAppinventoryService.controller;

import com.lobemus.shoppingAppinventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    //request url: localhost:8082/api/inventory/xiaomi_12t
    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("skuCode")String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
