package com.controlefacilWeb.demo.api;

import com.controlefacilWeb.demo.controllers.ClienteController;
import com.controlefacilWeb.demo.models.ClienteModel;
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

@Tag(name = "Clientes", description = "CRUD de clientes")
@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    private final ClienteController clienteController = new ClienteController();

    @Operation(summary = "Listar clientes", responses = {
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteModel.class))))
    })
    @GetMapping
    public ResponseEntity<List<ClienteModel>> listar() {
        try {
            return ResponseEntity.ok(converterLista(clienteController.ListarTodosClientes()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Buscar cliente por ID", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ClienteModel.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> buscar(@Parameter(description = "Código do cliente") @PathVariable int id) {
        ClienteModel cliente = clienteController.ProcurarCliente(id);
        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cadastrar cliente", responses = {
            @ApiResponse(responseCode = "201", description = "Criado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody ClienteModel cliente) {
        int resultado = clienteController.CadastrarCliente(cliente);
        return resultado != 0 ? ResponseEntity.status(201).build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Atualizar cliente", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@Parameter(description = "Código do cliente") @PathVariable int id, @RequestBody ClienteModel cliente) {
        cliente.setCodigoCliente(id);
        int resultado = clienteController.AlterarCliente(cliente);
        return resultado != 0 ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @SuppressWarnings("rawtypes")
    private List<ClienteModel> converterLista(ArrayList<ArrayList> rows) {
        List<ClienteModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                ClienteModel c = new ClienteModel();
                c.setCodigoCliente(Integer.parseInt(row.get(0).toString()));
                c.setNome(row.get(1).toString());
                if (row.size() > 2 && row.get(2) != null) c.setDocumento(row.get(2).toString());
                if (row.size() > 3 && row.get(3) != null) c.setSexo(row.get(3).toString());
                if (row.size() > 4 && row.get(4) != null) c.setNumerosus(row.get(4).toString());
                lista.add(c);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
