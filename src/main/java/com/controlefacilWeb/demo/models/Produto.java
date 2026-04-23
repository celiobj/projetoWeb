package com.controlefacilWeb.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade Produto - Mapeia a tabela 'produtos' do banco Access
 */
@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Column(nullable = false, length = 255)
    private String nome;

    @Column(unique = true, length = 50)
    @NotBlank(message = "Código de barras é obrigatório")
    private String codigoBarras;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que 0")
    @Column(nullable = false)
    private Double preco;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, updatable = false)
    private Long dataCriacao = System.currentTimeMillis();

    @Column(nullable = false)
    private Long dataAtualizacao = System.currentTimeMillis();
}
