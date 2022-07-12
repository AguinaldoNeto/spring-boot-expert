package io.github.aguinaldoneto.vendas.rest.controller;

import io.github.aguinaldoneto.vendas.entity.ItemPedido;
import io.github.aguinaldoneto.vendas.entity.Pedido;
import io.github.aguinaldoneto.vendas.rest.dto.InformacaoItemPedidoDTO;
import io.github.aguinaldoneto.vendas.rest.dto.InformacoesPedidoDTO;
import io.github.aguinaldoneto.vendas.rest.dto.PedidoDTO;
import io.github.aguinaldoneto.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired(required = false)
    private PedidoService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O pedido não foi encontrado."));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        InformacoesPedidoDTO pedidosDto = InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .total(pedido.getTotal())
                .items(converterItensPedido(pedido.getItens()))
                .build();

        return pedidosDto;
    }

    private List<InformacaoItemPedidoDTO> converterItensPedido(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream()
                .map(itemPedido -> InformacaoItemPedidoDTO.builder()
                        .descricaoProduto(itemPedido.getProduto().getDescricao())
                        .precoUnitario(itemPedido.getProduto().getPreco())
                        .quantidade(itemPedido.getQuantidade()).build())
                .collect(Collectors.toList());

    }

}
