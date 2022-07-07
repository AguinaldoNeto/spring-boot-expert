package io.github.aguinaldoneto.vendas.service.impl;

import io.github.aguinaldoneto.vendas.repository.PedidoRepository;
import io.github.aguinaldoneto.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;


}
