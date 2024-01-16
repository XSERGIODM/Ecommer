package com.app.services.impls;

import com.app.models.Orden;
import com.app.repositories.IOrdenRepository;
import com.app.services.IOrdenService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrdenServiceImpl implements IOrdenService {

    IOrdenRepository ordenRepository;
    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    //000010
    public String generarNUmeroOrden(){
        int numero;
        String numeroConcatenado;
        List<Orden> ordenes = ordenRepository.findAll();
        List<Integer> numeros = new ArrayList<>();
        ordenes.forEach(orden -> numeros.add(Integer.parseInt(orden.getNumero())));
        if(!numeros.isEmpty()){
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }else {
            numero = 1;
        }
        numeroConcatenado = String.format("%10d", numero);
        return numeroConcatenado;
    }
}
