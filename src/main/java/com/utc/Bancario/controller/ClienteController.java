package com.utc.Bancario.controller;

import com.utc.Bancario.entity.Cliente;
import com.utc.Bancario.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Listado de Clientes");
        return "clientes/listarClientes";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        return "clientes/crearClientes";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result,
                          RedirectAttributes attribute, Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("titulo", cliente.getId() != null ? "Editar Cliente" : "Nuevo Cliente");
            // Si hay error, advertencia suave y retornamos la misma vista
            return cliente.getId() != null ? "clientes/editarClientes" : "clientes/crearClientes";
        }

        clienteService.guardar(cliente);
        attribute.addFlashAttribute("success", "¡Cliente guardado con éxito!");
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes attribute) {
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente == null) {
            attribute.addFlashAttribute("error", "Error: El cliente no existe.");
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "clientes/editarClientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attribute) {
        clienteService.eliminar(id);
        attribute.addFlashAttribute("warning", "Registro eliminado correctamente.");
        return "redirect:/clientes";
    }
}