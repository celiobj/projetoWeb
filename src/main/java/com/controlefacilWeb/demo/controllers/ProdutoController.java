package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.Produto;
import com.controlefacilWeb.demo.services.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.ArrayList;

/**
 * Controller para Produto
 * Gerencia as requisições HTTP relacionadas a produtos
 */
@Controller
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    /**
     * GET /produtos - Exibe lista de todos os produtos
     */
    @GetMapping
    public String listar(Model model) {
        List<Produto> produtos = produtoService.obterTodos();
        model.addAttribute("produtos", produtos);
        model.addAttribute("totalProdutos", produtos.size());
        return "produtos/lista";
    }

    /**
     * GET /produtos/novo - Exibe formulário para criar novo produto
     */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/formulario";
    }

    /**
     * POST /produtos - Cria um novo produto
     */
    @PostMapping
    public String criar(@Valid @ModelAttribute Produto produto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "produtos/formulario";
        }
        try {
            produtoService.criar(produto);
            redirectAttributes.addFlashAttribute("mensagem", "Produto criado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");
            return "redirect:/produtos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao criar produto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/produtos/novo";
        }
    }

    /**
     * GET /produtos/{id} - Exibe detalhes de um produto
     */
    @GetMapping("/{id}")
    public String exibir(@PathVariable Long id, Model model) {
        return produtoService.obterPorId(id)
                .map(produto -> {
                    model.addAttribute("produto", produto);
                    return "produtos/detalhe";
                })
                .orElse("redirect:/produtos");
    }

    /**
     * GET /produtos/{id}/editar - Exibe formulário para editar produto
     */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        return produtoService.obterPorId(id)
                .map(produto -> {
                    model.addAttribute("produto", produto);
                    return "produtos/formulario";
                })
                .orElse("redirect:/produtos");
    }

    /**
     * PUT /produtos/{id} - Atualiza um produto existente
     */
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute Produto produto,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "produtos/formulario";
        }
        try {
            produtoService.atualizar(id, produto);
            redirectAttributes.addFlashAttribute("mensagem", "Produto atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");
            return "redirect:/produtos/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar produto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/produtos/" + id + "/editar";
        }
    }

    /**
     * DELETE /produtos/{id} - Deleta um produto
     */
    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            produtoService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Produto deletado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao deletar produto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/produtos";
    }

    /**
     * GET /produtos/buscar - Busca produtos por nome
     */
    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String termo, Model model) {
        List<Produto> produtos;
        if (termo != null && !termo.isEmpty()) {
            produtos = produtoService.buscarPorNome(termo);
        } else {
            produtos = produtoService.obterTodos();
        }
        model.addAttribute("produtos", produtos);
        model.addAttribute("termoBusca", termo);
        return "produtos/lista";
    }

    /**
     * GET /produtos/comestoque - Lista apenas produtos com estoque
     */
    @GetMapping("/comestoque")
    public String listarComEstoque(Model model) {
        List<Produto> produtos = produtoService.obterComEstoque();
        model.addAttribute("produtos", produtos);
        model.addAttribute("titulo", "Produtos em Estoque");
        return "produtos/lista";
    }

    /**
     * GET /produtos/codigo/{codigoBarras} - Busca produto pelo código de barras
     */
    @GetMapping("/codigo/{codigoBarras}")
    public String buscarPorCodigoBarras(@PathVariable String codigoBarras, Model model, 
                                       RedirectAttributes redirectAttributes) {
        return produtoService.buscarPorCodigoBarras(codigoBarras)
                .map(produto -> {
                    model.addAttribute("produto", produto);
                    return "produtos/detalhe";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensagem", "Produto com código de barras não encontrado");
                    redirectAttributes.addFlashAttribute("tipo", "erro");
                    return "redirect:/produtos";
                });
    }

   

    public ArrayList<ArrayList> listarTodosProdutos() {
        List<Produto> produtos = produtoService.obterTodos();
        ArrayList<ArrayList> resultado = new ArrayList<>();
        for (Produto p : produtos) {
            ArrayList<Object> linha = new ArrayList<>();
            linha.add(p.getId());
            linha.add(p.getNome());
            linha.add(p.getCodigoBarras());
            linha.add(p.getDescricao());
            linha.add(p.getPreco());
            linha.add(p.getQuantidade());
            resultado.add(linha);
        }
        return resultado;
    }

    public ProdutoController() {
        this.produtoService = null;
        //TODO Auto-generated constructor stub
    }
}
