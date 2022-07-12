package io.github.aguinaldoneto.vendas.repository;

import io.github.aguinaldoneto.vendas.entity.Cliente;
import io.github.aguinaldoneto.vendas.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);

    @Query(" select p FROM Pedido p "
            + "     LEFT JOIN FETCH p.itens "
            + " WHERE p.id = :id ")
    Optional<Pedido> findByIdFetchItens(
            @Param("id") Integer id);

}
