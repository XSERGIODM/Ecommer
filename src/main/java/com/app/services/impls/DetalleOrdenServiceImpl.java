package com.app.services.impls;

import com.app.models.DetalleOrden;
import com.app.repositories.IDetalleOrdenRepository;
import com.app.services.IDestalleOrdenService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DetalleOrdenServiceImpl implements IDestalleOrdenService {
    IDetalleOrdenRepository detalleOrdenRepository;
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
}
