package br.com.renatofonseca.teste_gerenciamentodetarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import br.com.renatofonseca.teste_gerenciamentodetarefas.entity.GerenciaTarefas;
import br.com.renatofonseca.teste_gerenciamentodetarefas.service.GerenciaService;

@RestController
@RequestMapping("/tarefas")
public class GerenciaController {

    @Autowired
    private GerenciaService gerenciaService;

    @PostMapping
    public Mono<GerenciaTarefas> createTask(@RequestBody GerenciaTarefas tarefa) {
        return gerenciaService.create(tarefa);
    }

    @GetMapping
    public Flux<GerenciaTarefas> getAllTasks() {
        return gerenciaService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Mono<GerenciaTarefas> getTaskById(@PathVariable Long id) {
        return gerenciaService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public Mono<GerenciaTarefas> updateTask(@PathVariable Long id, @RequestBody GerenciaTarefas tarefa) {
        return gerenciaService.updateTask(id, tarefa);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id) {
        return gerenciaService.deleteTask(id);
    }

    @GetMapping("/status/{realizada}")
    public Flux<GerenciaTarefas> getTasksByStatus(@PathVariable boolean realizada) {
        return gerenciaService.getTasksByStatus(realizada);
    }
}
