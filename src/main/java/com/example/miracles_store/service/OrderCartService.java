package com.example.miracles_store.service;

import com.example.miracles_store.entity.OrderCart;
import com.example.miracles_store.entity.SellPositionQuantity;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.OrderCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCartService {

    private final OrderCartRepository orderCartRepository;

    public OrderCart save(OrderCart orderCart) {
        if (!orderCartRepository.existsById(orderCart.getId())) {
            return orderCartRepository.save(orderCart);
        }

        OrderCart existingCart = getByUserId(orderCart.getId());
        List<SellPositionQuantity> existingSellPositionQuantityList = existingCart.getSellPositionQuantityList();
        List<SellPositionQuantity> sellPositionQuantityList = orderCart.getSellPositionQuantityList();

        for (SellPositionQuantity positionQuantity : sellPositionQuantityList) {
            boolean found = false;
            for (SellPositionQuantity sellPositionQuantity : existingSellPositionQuantityList) {
                if (Objects.equals(positionQuantity.getSellPositionId(), sellPositionQuantity.getSellPositionId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                existingSellPositionQuantityList.add(positionQuantity);
            }
        }
        return orderCartRepository.save(existingCart);
    }


    @Transactional(readOnly = true)
    public OrderCart getByUserId(Integer userId) {
        return orderCartRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(
                "Can't find order Cart by user with id: " + userId));
    }

    public void deleteSellPositionFromCart(OrderCart orderCart) {
        OrderCart storedOrderCart = getByUserId(orderCart.getId());
        List<SellPositionQuantity> storedSellPositionQuantityList = storedOrderCart.getSellPositionQuantityList();
        List<SellPositionQuantity> sellPositionQuantityList = orderCart.getSellPositionQuantityList();
        List<SellPositionQuantity> sellPositionQuantityToDeleteList = new ArrayList<>();

        for (SellPositionQuantity positionQuantity : sellPositionQuantityList) {
            SellPositionQuantity sellPositionQuantityToDelete = null;
            boolean found = false;
            for (SellPositionQuantity storedPositionQuantity : storedSellPositionQuantityList) {
                if (Objects.equals(positionQuantity.getSellPositionId(), storedPositionQuantity.getSellPositionId())) {
                    sellPositionQuantityToDelete = storedPositionQuantity;
                    found = true;
                    break;
                }
            }
            if (found) {
                sellPositionQuantityToDeleteList.add(sellPositionQuantityToDelete);
            }
        }
        storedSellPositionQuantityList.removeAll(sellPositionQuantityToDeleteList);
        if (storedOrderCart.getSellPositionQuantityList().isEmpty()) {
            orderCartRepository.delete(storedOrderCart);
        } else {
            orderCartRepository.save(storedOrderCart);
        }
    }

    public OrderCart setPositionQuantity(OrderCart orderCart) {
        OrderCart storedOrderCart = getByUserId(orderCart.getId());
        List<SellPositionQuantity> storedSellPositionQuantityList = storedOrderCart.getSellPositionQuantityList();
        List<SellPositionQuantity> sellPositionQuantityList = orderCart.getSellPositionQuantityList();

        for (SellPositionQuantity positionQuantity : sellPositionQuantityList) {
            for (SellPositionQuantity sellPositionQuantity : storedSellPositionQuantityList) {
                if (Objects.equals(positionQuantity.getSellPositionId(), sellPositionQuantity.getSellPositionId())) {
                    sellPositionQuantity.setQuantity(positionQuantity.getQuantity());
                    break;
                }
            }
        }
        return orderCartRepository.save(storedOrderCart);
    }
}
