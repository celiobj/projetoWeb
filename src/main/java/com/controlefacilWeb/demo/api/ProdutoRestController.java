package com.controlefacilWeb.demo.api;

import com.controlefacilWeb.demo.models.ProdutoModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioProduto;
import com.controlefacilWeb.demo.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Produtos", description = "CRUD de produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoRestController {

    private final RepositorioProduto rp = new RepositorioProduto();

    @Operation(summary = "Listar produtos", responses = {
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoModel.class))))
    })
    @GetMapping
    public ResponseEntity<List<ProdutoModel>> listar() {
        try {
            return ResponseEntity.ok(converterLista(rp.ListarTodosProdutos()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Buscar produto por ID", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ProdutoModel.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> buscar(@Parameter(description = "Código do produto") @PathVariable int id) {
        ProdutoModel produto = rp.Procurar(id);
        return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cadastrar produto", responses = {
            @ApiResponse(responseCode = "201", description = "Criado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody ProdutoModel produto) {
        int resultado = rp.CadastrarProduto(produto, new ArrayList<>());
        return resultado == 1 ? ResponseEntity.status(201).build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Atualizar produto", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@Parameter(description = "Código do produto") @PathVariable int id, @RequestBody ProdutoModel produto) {
        int resultado = rp.AlterararProduto(produto, id);
        return resultado == 1 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Desativar produto", description = "Soft delete: define isativo='N'", responses = {
            @ApiResponse(responseCode = "204", description = "Desativado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@Parameter(description = "Código do produto") @PathVariable int id) {
        // Soft delete: produto continua no banco com isativo='N'
        ProdutoModel produto = rp.Procurar(id);
        if (produto == null) return ResponseEntity.notFound().build();
        produto.setIsativo('N');
        int resultado = rp.AlterararProduto(produto, id);
        return resultado == 1 ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

    @SuppressWarnings("rawtypes")
    private List<ProdutoModel> converterLista(ArrayList<ArrayList> rows) {
        List<ProdutoModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        // Colunas: cdproduto, nome, descricao, quantidade, valormediocompra, valorvenda, valorcomissao, isativo
        for (ArrayList row : rows) {
            ProdutoModel p = new ProdutoModel();
            if (row.size() > 0 && row.get(0) != null) p.setCodigoProduto(Integer.parseInt(row.get(0).toString()));
            if (row.size() > 1 && row.get(1) != null) p.setNome(row.get(1).toString());
            if (row.size() > 2 && row.get(2) != null) p.setDescricao(row.get(2).toString());
            if (row.size() > 3 && row.get(3) != null) p.setQuantidade(Integer.parseInt(row.get(3).toString()));
            if (row.size() > 4 && row.get(4) != null) p.setValormMedioCompra(Double.parseDouble(Util.textoParaDouble(row.get(4).toString())));
            if (row.size() > 5 && row.get(5) != null) p.setValorvenda(Double.parseDouble(Util.textoParaDouble(row.get(5).toString())));
            if (row.size() > 6 && row.get(6) != null) p.setValorcomissao(Double.parseDouble(Util.textoParaDouble(row.get(6).toString())));
            if (row.size() > 7 && row.get(7) != null) {
                String v = row.get(7).toString();
                p.setIsativo(v.isEmpty() ? 'N' : v.charAt(0));
            }
            lista.add(p);
        }
        return lista;
    }
}
