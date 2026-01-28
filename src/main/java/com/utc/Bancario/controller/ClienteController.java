package com.utc.Bancario.controller;

import com.utc.Bancario.entity.Cliente;
import com.utc.Bancario.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        
        // Validación de Negocio para duplicados por cédula
        if (clienteService.existeCedula(cliente.getCedula(), cliente.getId())) {
            result.rejectValue("cedula", "error.cedula", "Esta cédula ya está registrada.");
        }

        // Validación de Formato (Entidad) + Resultado de la anterior
        if (result.hasErrors()) {
            model.addAttribute("titulo", cliente.getId() != null ? "Editar Cliente" : "Nuevo Cliente");
            return cliente.getId() != null ? "clientes/editarClientes" : "clientes/crearClientes";
        }

        try {
            clienteService.guardar(cliente);
            attribute.addFlashAttribute("success", "¡Cliente guardado con éxito!");
        } catch (DataIntegrityViolationException e) {
            // Capturar errores de integridad de datos (constraints duplicados en la BD)
            String errorMsg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";

            if (errorMsg.contains("cedula")) {
                result.rejectValue("cedula", "error.cedula", "Esta cédula ya está registrada.");
            } else {
                attribute.addFlashAttribute("error", "Error al guardar el cliente. Verifique los datos.");
                return "redirect:/clientes";
            }

            model.addAttribute("titulo", cliente.getId() != null ? "Editar Cliente" : "Nuevo Cliente");
            return cliente.getId() != null ? "clientes/editarClientes" : "clientes/crearClientes";
        }

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