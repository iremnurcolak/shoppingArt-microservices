package com.lobemus.shoppingAppinventoryService.controller;

import com.lobemus.shoppingAppinventoryService.dto.InventoryResponse;
import com.lobemus.shoppingAppinventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    //request url: localhost:8082/api/inventory/?sku-code=xiaomi_12t&sku-code=iphone_13
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
