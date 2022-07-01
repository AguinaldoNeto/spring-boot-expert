package io.github.aguinaldoneto.vendas.entity;

import javax.persistence.*;

@Entity
@Table
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Produto produto;

    @Column
    private Integer quantidade;

}
