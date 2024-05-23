package com.example.miracles_store.service;

import com.example.miracles_store.dto.filter.AddressFilter;
import com.example.miracles_store.entity.Address;
import com.example.miracles_store.entity.QAddress;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.AddressRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public Page<Address> getAll(AddressFilter addressFilter, Pageable pageable) {
        QAddress qAddress = QAddress.address;
        BooleanBuilder predicate = new BooleanBuilder();

        if (addressFilter.city() != null) {
            predicate.and(qAddress.city.containsIgnoreCase(addressFilter.city()));
        }
        if (addressFilter.street() != null) {
            predicate.and(qAddress.street.containsIgnoreCase(addressFilter.street()));
        }
        if (addressFilter.house() != null) {
            predicate.and(qAddress.house.containsIgnoreCase(addressFilter.house()));
        }
        if (addressFilter.flat() != null) {
            predicate.and(qAddress.flat.containsIgnoreCase(addressFilter.flat()));
        }
        if (addressFilter.zipCode() != null) {
            predicate.and(qAddress.zipCode.eq(addressFilter.zipCode()));
        }
        if (addressFilter.userId() != null) {
            predicate.and((qAddress.user.id.eq(addressFilter.userId())));
        }
        return addressRepository.findAll(predicate, pageable);
    }

    @Transactional(readOnly = true)
    public Address getById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Can't find address with id " + id));
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address update(Address address) {
        return addressRepository.saveAndFlush(address);
    }

    public void deleteById(Integer id) {
        addressRepository.deleteById(id);
    }
}
