package com.controlefacilWeb.demo.services;

import com.controlefacilWeb.demo.models.Produto;
import com.controlefacilWeb.demo.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para Produto
 * Contém a lógica de negócio
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    /**
     * Obtém todos os produtos
     */
    @Transactional(readOnly = true)
    public List<Produto> obterTodos() {
        return produtoRepository.findAll();
    }

    /**
     * Obtém um produto pelo ID
     */
    @Transactional(readOnly = true)
    public Optional<Produto> obterPorId(Long id) {
        return produtoRepository.findById(id);
    }

    /**
     * Cria um novo produto
     */
    public Produto criar(Produto produto) {
        produto.setDataCriacao(System.currentTimeMillis());
        produto.setDataAtualizacao(System.currentTimeMillis());
        return produtoRepository.save(produto);
    }

    /**
     * Atualiza um produto existente
     */
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setCodigoBarras(produtoAtualizado.getCodigoBarras());
                    produto.setDescricao(produtoAtualizado.getDescricao());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setQuantidade(produtoAtualizado.getQuantidade());
                    produto.setDataAtualizacao(System.currentTimeMillis());
                    return produtoRepository.save(produto);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    /**
     * Deleta um produto
     */
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    /**
     * Busca produtos pelo nome
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Busca produto pelo código de barras
     */
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorCodigoBarras(String codigoBarras) {
        return produtoRepository.findByCodigoBarras(codigoBarras);
    }

    /**
     * Busca produtos por intervalo de preço
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorPrecoPorIntervalo(Double precoMin, Double precoMax) {
        return produtoRepository.findByPrecoBetween(precoMin, precoMax);
    }

    /**
     * Busca produtos com estoque disponível
     */
    @Transactional(readOnly = true)
    public List<Produto> obterComEstoque() {
        return produtoRepository.findByQuantidadeGreaterThan(0);
    }

    /**
     * Reduz o estoque de um produto
     */
    public Produto reduzirEstoque(Long id, Integer quantidade) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    if (produto.getQuantidade() < quantidade) {
                        throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
                    }
                    produto.setQuantidade(produto.getQuantidade() - quantidade);
                    produto.setDataAtualizacao(System.currentTimeMillis());
                    return produtoRepository.save(produto);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }
}
