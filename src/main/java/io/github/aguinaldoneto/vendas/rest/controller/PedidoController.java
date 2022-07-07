package io.github.aguinaldoneto.vendas.rest.controller;

import io.github.aguinaldoneto.vendas.entity.Pedido;
import io.github.aguinaldoneto.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Pedido save(@RequestBody Pedido pedido) {
        return service.save(pedido);
    }

    @GetMapping("/{id}")
    public Pedido getById(@PathVariable Integer id) {
        return service
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O pedido não foi encontrado."));
    }

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    public void updatePedido(@PathVariable Integer id, @RequestBody Pedido pedido) {
        service.findById(id)
                .map(pedidoExistente -> {
                    pedido.setId(pedidoExistente.getId());
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O pedido não foi encontrado."));
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deletarPedido(@PathVariable Integer id) {
        service.findById(id)
                .map(pedidoExistente -> {
                    repository.delete(pedidoExistente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "O pedido não foi encontrado."));
    }
}
