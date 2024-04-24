package com.example.miracles_store.service;

import com.example.miracles_store.dto.filter.AddressFilter;
import com.example.miracles_store.dto.filter.UserFilter;
import com.example.miracles_store.entity.QUser;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.exception.ReferencedEntityException;
import com.example.miracles_store.repository.AddressRepository;
import com.example.miracles_store.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public Page<User> getAll(UserFilter filter, AddressFilter addressFilter, Pageable pageable) {
        QUser qUser = QUser.user;
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.firstName() != null) {
            predicate.and(qUser.firstName.containsIgnoreCase(filter.firstName()));
        }
        if (filter.lastName() != null) {
            predicate.and(qUser.lastName.eq(filter.lastName()));
        }
        if (filter.email() != null) {
            predicate.and(qUser.email.eq(filter.email()));
        }
        if (addressFilter.city() != null) {
            predicate.and(qUser.addresses.any().city.containsIgnoreCase(addressFilter.city()));
        }
        if (addressFilter.street() != null) {
            predicate.and(qUser.addresses.any().street.containsIgnoreCase(addressFilter.street()));
        }
        if (addressFilter.house() != null) {
            predicate.and(qUser.addresses.any().house.containsIgnoreCase(addressFilter.house()));
        }
        if (addressFilter.flat() != null) {
            predicate.and(qUser.addresses.any().flat.containsIgnoreCase(addressFilter.flat()));
        }
        if (addressFilter.zipCode() != null) {
            predicate.and(qUser.addresses.any().zipCode.containsIgnoreCase(addressFilter.zipCode()));
        }
        return userRepository.findAll(predicate, pageable);
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Can't find user with email: " + email));
    }

    @Transactional(readOnly = true)
    public User getById (Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Can't find user with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), grantedAuthoritySet);
    }

    public void deleteById(Integer id) {
        User user = getById(id);

        if (addressRepository.existsByUser(user)) {
            throw new ReferencedEntityException(String
                    .format("Can't delete user '%s' due to existing addresses", user.getEmail()));
        }
        userRepository.deleteById(id);
    }

    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
