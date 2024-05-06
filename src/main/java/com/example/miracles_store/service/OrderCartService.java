package com.example.miracles_store.service;

import com.example.miracles_store.entity.OrderCart;
import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.OrderCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCartService {

    private final OrderCartRepository orderCartRepository;

    public OrderCart save(OrderCart orderCart) {
        if (!orderCartRepository.existsById(orderCart.getId())) {
            return orderCartRepository.save(orderCart);
        }

        OrderCart existingCart = getUserCart(orderCart.getId());
        Set<SellPositionQuantity> existingSellPositionQuantities = existingCart.getSellPositionQuantitySet();

        existingSellPositionQuantities.addAll(orderCart.getSellPositionQuantitySet());
        return orderCartRepository.save(existingCart);
    }



    @Transactional(readOnly = true)
    public OrderCart getUserCart(Integer userId) {
        return orderCartRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(
                "Can't find order Cart by user with id: " + userId));
    }

    public void deleteSellPositionFromCart(OrderCart orderCart) {
        OrderCart storedOrderCart = getUserCart(orderCart.getId());
        Set<SellPositionQuantity> sellPositionQuantitySet = storedOrderCart.getSellPositionQuantitySet();
        Set<SellPositionQuantity> sellPositionQuantityToDeleteSet = orderCart.getSellPositionQuantitySet();

        sellPositionQuantitySet.removeIf(sellPositionQuantity ->
                sellPositionQuantityToDeleteSet.stream()
                        .anyMatch(sellPositionQuantityToDelete ->
                                sellPositionQuantity.getSellPositionId()
                                        .equals(sellPositionQuantityToDelete.getSellPositionId())));
        if (storedOrderCart.getSellPositionQuantitySet().isEmpty()) {
            orderCartRepository.delete(storedOrderCart);
        } else {
            orderCartRepository.save(storedOrderCart);
        }
    }

    public OrderCart updatePositionQuantity(OrderCart orderCart) {
        OrderCart storedOrderCart = getUserCart(orderCart.getId());
        Set<SellPositionQuantity> sellPositionQuantitySet = storedOrderCart.getSellPositionQuantitySet();

        sellPositionQuantitySet.stream()
                .filter(sellPositionQuantity -> orderCart.getSellPositionQuantitySet().stream()
                        .anyMatch(positionQuantity -> positionQuantity.getSellPositionId().equals(sellPositionQuantity.getSellPositionId())))
                .forEach(sellPositionQuantity -> sellPositionQuantity.setQuantity(
                        orderCart.getSellPositionQuantitySet().stream()
                                .filter(positionQuantity -> positionQuantity.getSellPositionId().equals(sellPositionQuantity.getSellPositionId()))
                                .findFirst()
                                .map(SellPositionQuantity::getQuantity)
                                .orElse(sellPositionQuantity.getQuantity())
                ));
        return orderCartRepository.save(storedOrderCart);
    }

}
