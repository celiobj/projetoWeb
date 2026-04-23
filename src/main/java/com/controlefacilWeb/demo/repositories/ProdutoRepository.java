package com.controlefacilWeb.demo.repositories;

import com.controlefacilWeb.demo.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Produto
 * Fornece operações CRUD e queries customizadas
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Busca produtos pelo nome (case-insensitive)
     */
    List<Produto> findByNomeIgnoreCase(String nome);

    /**
     * Busca produtos que contêm texto no nome
     */
    List<Produto> findByNomeContainingIgnoreCase(String termo);

    /**
     * Busca produtos por intervalo de preço
     */
    List<Produto> findByPrecoBetween(Double precoMin, Double precoMax);

    /**
     * Busca produto pelo código de barras
     */
    Optional<Produto> findByCodigoBarras(String codigoBarras);

    /**
     * Busca produtos com estoque (quantidade > 0)
     */
    List<Produto> findByQuantidadeGreaterThan(Integer quantidade);
}
