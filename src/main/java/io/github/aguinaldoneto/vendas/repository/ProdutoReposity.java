package io.github.aguinaldoneto.vendas.repository;

import io.github.aguinaldoneto.vendas.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoReposity extends JpaRepository<Produto, Integer> {

}
