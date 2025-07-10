package com.springmusicapp.mapper;

import com.springmusicapp.model.Band;

public interface BandMapper<T extends Band, D> {
    D toDto(T entity);
}
