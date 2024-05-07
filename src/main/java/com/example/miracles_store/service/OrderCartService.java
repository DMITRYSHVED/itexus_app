package com.example.miracles_store.service;

import com.example.miracles_store.entity.order.OrderCart;
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

    public OrderCart saveSellPositionToCart(OrderCart orderCart) {
        if (!orderCartRepository.existsById(orderCart.getId())) {
            return save(orderCart);
        }

        OrderCart existingCart = getUserCart(orderCart.getId());
        Set<SellPositionQuantity> existingSellPositionQuantities = existingCart.getSellPositionQuantitySet();

        existingSellPositionQuantities.addAll(orderCart.getSellPositionQuantitySet());
        return update(existingCart);
    }

    protected OrderCart save(OrderCart orderCart) {
        return orderCartRepository.save(orderCart);
    }

    protected OrderCart update(OrderCart orderCart) {
        return orderCartRepository.save(orderCart);
    }

    protected void deleteById(Integer userId) {
        orderCartRepository.deleteById(userId);
    }

    protected void delete(OrderCart orderCart) {
        orderCartRepository.delete(orderCart);
    }

    @Transactional(readOnly = true)
    public OrderCart getUserCart(Integer userId) {
        return orderCartRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(
                "Can't find order Cart by user with id: " + userId));
    }

    public void deleteSellPositionFromCart(Integer userId, SellPositionQuantity sellPositionQuantity) {
        OrderCart storedOrderCart = getUserCart(userId);
        Set<SellPositionQuantity> sellPositionQuantitySet = storedOrderCart.getSellPositionQuantitySet();

        sellPositionQuantitySet.removeIf(storedSellPositionQuantity ->
                storedSellPositionQuantity.getSellPositionId().equals(sellPositionQuantity.getSellPositionId()));
        if (sellPositionQuantitySet.isEmpty()) {
            delete(storedOrderCart);
        } else {
            update(storedOrderCart);
        }
    }

    public OrderCart updatePositionQuantity(Integer userId, SellPositionQuantity sellPositionQuantity) {
        OrderCart storedOrderCart = getUserCart(userId);
        Set<SellPositionQuantity> sellPositionQuantitySet = storedOrderCart.getSellPositionQuantitySet();

        sellPositionQuantitySet.stream()
                .filter(storedSellPositionQuantity ->
                        storedSellPositionQuantity.getSellPositionId().equals(sellPositionQuantity.getSellPositionId()))
                .findFirst().ifPresent(storedSellPositionQuantity ->
                        storedSellPositionQuantity.setQuantity(sellPositionQuantity.getQuantity()));
        return update(storedOrderCart);
    }
}
