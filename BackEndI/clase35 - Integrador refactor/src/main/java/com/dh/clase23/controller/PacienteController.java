package com.dh.clase23.controller;

import com.dh.clase23.model.Paciente;
import com.dh.clase23.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> pacienteListar(){
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> pacienteBuscar(@PathVariable Long id){
        Optional<Paciente> response = pacienteService.buscarPacienteID(id);
        if (response.isPresent()){
            return ResponseEntity.ok(response.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Paciente> pacienteRegistrar(@RequestBody Paciente paciente){
        Paciente response = pacienteService.guardarPaciente(paciente);
        if (response.getId() != null){
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping
    public ResponseEntity<String> pacienteActualizar(@RequestBody Paciente paciente){
        if (pacienteService.buscarPacienteID(paciente.getId()).isPresent()){
            pacienteService.modificarPaciente(paciente);
            return ResponseEntity.ok("Se actualizó el paciente id = " + paciente.getId());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> pacienteEliminar(@PathVariable Long id){
        if (pacienteService.buscarPacienteID(id).isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Se eliminó el paciente id = " + id);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
