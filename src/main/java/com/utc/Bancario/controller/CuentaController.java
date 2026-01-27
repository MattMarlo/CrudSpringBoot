package com.utc.Bancario.controller;

import com.utc.Bancario.entity.Cuenta;
import com.utc.Bancario.service.ClienteService;
import com.utc.Bancario.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cuentas", cuentaService.listarTodas());
        model.addAttribute("titulo", "Listado de Cuentas");
        return "cuentas/listarCuentas";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("cuenta", new Cuenta());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Nueva Cuenta");
        return "cuentas/crearCuentas";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cuenta cuenta, BindingResult result, 
                          Model model, RedirectAttributes attribute) {
        
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("titulo", cuenta.getId() != null ? "Editar Cuenta" : "Nueva Cuenta");
            return cuenta.getId() != null ? "cuentas/editarCuentas" : "cuentas/crearCuentas";
        }

        cuentaService.guardar(cuenta);
        attribute.addFlashAttribute("success", "Cuenta guardada correctamente");
        return "redirect:/cuentas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes attribute) {
        Cuenta cuenta = cuentaService.obtenerPorId(id);
        if (cuenta == null) {
            attribute.addFlashAttribute("error", "La cuenta no existe");
            return "redirect:/cuentas";
        }
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Editar Cuenta");
        return "cuentas/editarCuentas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attribute) {
        cuentaService.eliminar(id);
        attribute.addFlashAttribute("warning", "Cuenta eliminada con éxito");
        return "redirect:/cuentas";
    }
}