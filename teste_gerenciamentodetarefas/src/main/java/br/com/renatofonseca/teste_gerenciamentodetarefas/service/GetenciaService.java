package br.com.renatofonseca.teste_gerenciamentodetarefas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import br.com.renatofonseca.teste_gerenciamentodetarefas.entity.GerenciaTarefas;
import br.com.renatofonseca.teste_gerenciamentodetarefas.repository.GerenciaTarefasRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GerenciaService {

    private final List<GerenciaTarefas> tarefasEmMemoria = new CopyOnWriteArrayList<>();

    @Autowired
    private GerenciaTarefasRepository gerenciaTarefasRepository;

    @PostConstruct
    private void init() {
        // Carrega tarefas do MongoDB para a lista em memória ao iniciar
        gerenciaTarefasRepository.findAll().collectList().subscribe(tarefasEmMemoria::addAll);
    }

    public Mono<GerenciaTarefas> create(GerenciaTarefas tarefa) {
        tarefasEmMemoria.add(tarefa);
        return gerenciaTarefasRepository.save(tarefa);
    }

    public Flux<GerenciaTarefas> getAllTasks() {
        return Flux.fromIterable(tarefasEmMemoria);
    }

    public Mono<GerenciaTarefas> getTaskById(Long id) {
        return Mono.justOrEmpty(tarefasEmMemoria.stream()
            .filter(tarefa -> tarefa.getId().equals(id))
            .findFirst());
    }

    public Mono<GerenciaTarefas> updateTask(Long id, GerenciaTarefas tarefa) {
        return getTaskById(id).flatMap(existingTask -> {
            existingTask.setNome(tarefa.getNome());
            existingTask.setDescricao(tarefa.getDescricao());
            existingTask.setRealizada(tarefa.isRealizada());
            existingTask.setPrioridade(tarefa.getPrioridade());
            return gerenciaTarefasRepository.save(existingTask).doOnSuccess(updatedTask -> {
                tarefasEmMemoria.removeIf(t -> t.getId().equals(id));
                tarefasEmMemoria.add(updatedTask);
            });
        });
    }

    public Mono<Void> deleteTask(Long id) {
        return getTaskById(id).flatMap(tarefa -> {
            tarefasEmMemoria.removeIf(t -> t.getId().equals(id));
            return gerenciaTarefasRepository.deleteById(id);
        });
    }

    public Flux<GerenciaTarefas> getTasksByStatus(boolean realizada) {
        return Flux.fromIterable(tarefasEmMemoria)
            .filter(tarefa -> tarefa.isRealizada() == realizada);
    }
}
