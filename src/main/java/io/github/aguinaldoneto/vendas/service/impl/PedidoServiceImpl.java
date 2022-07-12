package io.github.aguinaldoneto.vendas.service.impl;

import io.github.aguinaldoneto.vendas.entity.Cliente;
import io.github.aguinaldoneto.vendas.entity.ItemPedido;
import io.github.aguinaldoneto.vendas.entity.Pedido;
import io.github.aguinaldoneto.vendas.entity.Produto;
import io.github.aguinaldoneto.vendas.exception.RegraNegocioException;
import io.github.aguinaldoneto.vendas.repository.ClienteRepository;
import io.github.aguinaldoneto.vendas.repository.ItemPedidoRepository;
import io.github.aguinaldoneto.vendas.repository.PedidoRepository;
import io.github.aguinaldoneto.vendas.repository.ProdutoReposity;
import io.github.aguinaldoneto.vendas.rest.dto.ItemPedidoDTO;
import io.github.aguinaldoneto.vendas.rest.dto.PedidoDTO;
import io.github.aguinaldoneto.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoReposity produtoReposity;

    @Autowired
    private ItemPedidoRepository itemsPedidoRepository;

    @Override
    @Transactional  // ou acontece tudo com sucesso e ele salva ou se tiver problema, nada será salvo.
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente(); //pegando o id do cliente vindo do DTO.
        Cliente cliente = clienteRepository // Se tiver esse id na tabela de Cliente pega, se não lança a exceção.
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente); //Passar para o Cliente do pedido o Cliente otido do BD.
        pedido.setDataPedido(LocalDate.now());
        pedido.setTotal(dto.getTotal());

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Código de produto inválido.");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto(); //pega o id do produto vindo do DTO
                    Produto produto = produtoReposity // se esse produto existe, pega ele ou lança exceção.
                            .findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código produto inválido: ." + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
