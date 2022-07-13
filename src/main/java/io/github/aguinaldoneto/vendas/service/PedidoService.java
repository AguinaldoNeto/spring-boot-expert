package io.github.aguinaldoneto.vendas.service;

import io.github.aguinaldoneto.vendas.entity.Pedido;
import io.github.aguinaldoneto.vendas.enums.StatusPedido;
import io.github.aguinaldoneto.vendas.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
