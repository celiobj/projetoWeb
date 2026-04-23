package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.ProdutoModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioProduto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.controlefacilWeb.demo.util.Util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final RepositorioProduto rp;

    public ProdutoController() {
        this.rp = new RepositorioProduto();
    }

    // Compatibilidade com LojaController que chama new ProdutoController(null)
    public ProdutoController(Connection con) {
        this.rp = new RepositorioProduto();
    }

    // ---------------------------------------------------------------
    // Endpoints Spring MVC
    // ---------------------------------------------------------------

    @GetMapping
    public String listar(Model model) {
        List<ProdutoModel> produtos;
        try {
            ArrayList<ArrayList> rows = rp.ListarTodosProdutos();
            produtos = converterLista(rows);
        } catch (Exception e) {
            e.printStackTrace();
            produtos = new ArrayList<>();
        }
        model.addAttribute("produtos", produtos);
        model.addAttribute("totalProdutos", produtos.size());
        return "produtos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new ProdutoModel());
        return "produtos/formulario";
    }

    @PostMapping
    public String criar(@ModelAttribute ProdutoModel produto, RedirectAttributes redirectAttributes) {
        try {
            int resultado = rp.CadastrarProduto(produto, new ArrayList<>());
            if (resultado == 1) {
                redirectAttributes.addFlashAttribute("mensagem", "Produto criado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
                return "redirect:/produtos";
            }
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao criar produto.");
            redirectAttributes.addFlashAttribute("tipo", "erro");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao criar produto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/produtos/novo";
    }

    @GetMapping("/{id}")
    public String exibir(@PathVariable int id, Model model) {
        ProdutoModel produto = rp.Procurar(id);
        if (produto == null) return "redirect:/produtos";
        model.addAttribute("produto", produto);
        return "produtos/detalhe";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable int id, Model model) {
        ProdutoModel produto = rp.Procurar(id);
        if (produto == null) return "redirect:/produtos";
        model.addAttribute("produto", produto);
        return "produtos/formulario";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable int id, @ModelAttribute ProdutoModel produto,
                            RedirectAttributes redirectAttributes) {
        try {
            int resultado = rp.AlterararProduto(produto, id);
            if (resultado == 1) {
                redirectAttributes.addFlashAttribute("mensagem", "Produto atualizado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
                return "redirect:/produtos/" + id;
            }
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar produto.");
            redirectAttributes.addFlashAttribute("tipo", "erro");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar produto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/produtos/" + id + "/editar";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagem", "Exclusão não disponível nesta versão.");
        redirectAttributes.addFlashAttribute("tipo", "erro");
        return "redirect:/produtos";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String termo, Model model) {
        ArrayList<ArrayList> rows = rp.ListarTodosProdutos();
        List<ProdutoModel> produtos = converterLista(rows);
        if (termo != null && !termo.isEmpty()) {
            String filtro = termo.toLowerCase();
            produtos = produtos.stream()
                    .filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(filtro))
                    .collect(Collectors.toList());
        }
        model.addAttribute("produtos", produtos);
        model.addAttribute("termoBusca", termo);
        return "produtos/lista";
    }

    // ---------------------------------------------------------------
    // Método legado — chamado por LojaController
    // ---------------------------------------------------------------

    public ArrayList<ArrayList> listarTodosProdutos() {
        return rp.ListarTodosProdutos();
    }

    // ---------------------------------------------------------------
    // Helper de conversão de linhas brutas → ProdutoModel
    // ---------------------------------------------------------------

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
            if (row.size() > 4 && row.get(4) != null) p.setValormMedioCompra(Double.parseDouble( Util.textoParaDouble(row.get(4).toString())));
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
