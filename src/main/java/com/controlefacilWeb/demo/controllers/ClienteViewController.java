package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.ClienteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteViewController {

    private final ClienteController clienteController;

    public ClienteViewController() {
        this.clienteController = new ClienteController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<ClienteModel> clientes = converterLista(clienteController.ListarTodosClientes());
        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "cliente/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new ClienteModel());
        return "cliente/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ClienteModel cliente, RedirectAttributes redirectAttributes) {
        try {
            int resultado = clienteController.CadastrarCliente(cliente);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar cliente.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        ClienteModel cliente = clienteController.ProcurarCliente(id);
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Cliente não encontrado.");
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "cliente/formulario";
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable int id, @ModelAttribute ClienteModel cliente,
                          RedirectAttributes redirectAttributes) {
        try {
            cliente.setCodigoCliente(id);
            int resultado = clienteController.AlterarCliente(cliente);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Cliente atualizado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar cliente.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/clientes";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable int id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagem", "Remoção de clientes não está disponível nesta versão.");
        redirectAttributes.addFlashAttribute("tipo", "erro");
        return "redirect:/clientes";
    }

    @SuppressWarnings("rawtypes")
    private List<ClienteModel> converterLista(ArrayList<ArrayList> rows) {
        List<ClienteModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                int codigoCliente = Integer.parseInt(row.get(0).toString());
                String nome       = row.get(1).toString();
                String documento  = row.size() > 2 && row.get(2) != null ? row.get(2).toString() : "";
                String sexo       = row.size() > 3 && row.get(3) != null ? row.get(3).toString() : "";
                String numerosus  = row.size() > 4 && row.get(4) != null ? row.get(4).toString() : "";
                ClienteModel c = new ClienteModel();
                c.setCodigoCliente(codigoCliente);
                c.setNome(nome);
                c.setDocumento(documento);
                c.setSexo(sexo);
                c.setNumerosus(numerosus);
                lista.add(c);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
