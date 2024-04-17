package com.example.miracles_store.controller;

import com.example.miracles_store.dto.SellPositionRequestDto;
import com.example.miracles_store.dto.SellPositionResponseDto;
import com.example.miracles_store.dto.filter.SellPositionFilter;
import com.example.miracles_store.mapper.SellPositionMapper;
import com.example.miracles_store.service.SellPositionService;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "sellPosition_controller")
@RestController
@RequestMapping("/api/v1/sellPositions")
@RequiredArgsConstructor
public class SellPositionController {

    private final SellPositionService sellPositionService;

    private final SellPositionMapper sellPositionMapper;

    @GetMapping
    public ResponseEntity<List<SellPositionResponseDto>> getAll(SellPositionFilter sellPositionFilter,
                                                                @PageableDefault(size = 20, sort = "id")
                                                                Pageable pageable) {
        List<SellPositionResponseDto> response = sellPositionService.getAll(sellPositionFilter, pageable)
                .getContent().stream().map(sellPositionMapper::toResponseDto).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellPositionResponseDto> getById(@PathVariable Integer id) {
        SellPositionResponseDto response = sellPositionMapper.toResponseDto(sellPositionService.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SellPositionResponseDto> create(@RequestBody @Validated({Default.class, CreateAction.class})
                                                          SellPositionRequestDto sellPosition) {
        SellPositionResponseDto response = sellPositionMapper.toResponseDto(sellPositionService.
                save(sellPositionMapper.requestDtoToSellPosition(sellPosition)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SellPositionResponseDto> update(@RequestBody @Validated({Default.class, UpdateAction.class})
                                                          SellPositionRequestDto sellPosition) {
        SellPositionResponseDto response = sellPositionMapper.toResponseDto(sellPositionService
                .update(sellPositionMapper.requestDtoToSellPosition(sellPosition)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        sellPositionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
