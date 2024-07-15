package br.com.renatofonseca.teste_gerenciamentodetarefas.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import br.com.renatofonseca.teste_gerenciamentodetarefas.entity.GerenciaTarefas;

public interface GerenciaTarefasRepository extends ReactiveCrudRepository<GerenciaTarefas, Long> {
    Flux<GerenciaTarefas> findByRealizada(boolean realizada);
}
