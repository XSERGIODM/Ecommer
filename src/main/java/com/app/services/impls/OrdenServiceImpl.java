package com.app.services.impls;

import com.app.models.Orden;
import com.app.repositories.IOrdenRepository;
import com.app.services.IOrdenService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrdenServiceImpl implements IOrdenService {

    IOrdenRepository ordenRepository;
    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }
}
