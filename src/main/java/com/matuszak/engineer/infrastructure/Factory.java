package com.matuszak.engineer.infrastructure;

import org.springframework.stereotype.Component;

public interface Factory<E, D> {
    D create(E properties);
}
