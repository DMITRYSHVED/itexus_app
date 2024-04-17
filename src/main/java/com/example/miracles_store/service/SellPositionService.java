package com.example.miracles_store.service;

import com.example.miracles_store.dto.filter.SellPositionFilter;
import com.example.miracles_store.entity.QSellPosition;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.SellPositionRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SellPositionService {

    private final SellPositionRepository sellPositionRepository;

    public SellPosition getById(Integer id) {
        return sellPositionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Can't find sellPosition with id " + id));
    }

    @Transactional(readOnly = true)
    public Page<SellPosition> getAll(SellPositionFilter sellPositionFilter, Pageable pageable) {
        QSellPosition qSellPosition = QSellPosition.sellPosition;
        BooleanBuilder predicate = new BooleanBuilder();

        if (sellPositionFilter.productId() != null) {
            predicate.and(qSellPosition.product.id.eq(sellPositionFilter.productId()));
        }
        if (sellPositionFilter.positionSize() != null) {
            predicate.and(qSellPosition.size.containsIgnoreCase(sellPositionFilter.positionSize()));
        }
        if (sellPositionFilter.quantity() != null) {
            predicate.and(qSellPosition.quantity.eq(sellPositionFilter.quantity()));
        }
        if (sellPositionFilter.isActive() != null) {
            predicate.and(qSellPosition.isActive.eq(sellPositionFilter.isActive()));
        }
        return sellPositionRepository.findAll(predicate, pageable);
    }

    public SellPosition save(SellPosition sellPosition) {
        return sellPositionRepository.save(sellPosition);
    }

    public SellPosition update(SellPosition sellPosition) {
        return sellPositionRepository.saveAndFlush(sellPosition);
    }

    public void deleteById(Integer id) {
        sellPositionRepository.deleteById(id);
    }
}
