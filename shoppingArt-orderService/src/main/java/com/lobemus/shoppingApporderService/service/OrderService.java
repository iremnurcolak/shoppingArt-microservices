package com.lobemus.shoppingApporderService.service;

import com.lobemus.shoppingApporderService.dto.InventoryResponse;
import com.lobemus.shoppingApporderService.dto.OrderLineItemsDto;
import com.lobemus.shoppingApporderService.dto.OrderRequest;
import com.lobemus.shoppingApporderService.model.Order;
import com.lobemus.shoppingApporderService.model.OrderLineItems;
import com.lobemus.shoppingApporderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    //var name should be same with the bean method name
    private final WebClient.Builder webClientBuilder;
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList =
                orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToOrder).toList();

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                //.map(orderLineItems -> {return orderLineItems.getSkuCode();})
                .map(OrderLineItems::getSkuCode).toList();
        //TODO
        //stok kontrol edilirken sipariş edilen miktar da inventoryService'e gonderilmeli
        //stok sipariş icin yetersizse stokta kalan miktar donulmeli

        //Call Inventory service, and place order if product is in stock
        //SYNC Communication
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                                        .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponses)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if(allProductsInStock) {
            orderRepository.save(order);
            return "Order placed successfully!";
        }
        else {
            throw new IllegalArgumentException("Product is not in the stock, try again later!");
        }
    }

    private OrderLineItems mapToOrder(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

}
