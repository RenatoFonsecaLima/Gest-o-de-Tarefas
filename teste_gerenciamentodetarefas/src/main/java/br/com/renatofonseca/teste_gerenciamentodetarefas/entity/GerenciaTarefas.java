package br.com.renatofonseca.teste_gerenciamentodetarefas.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.GeneratedValue;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "gerenciamentosTarefas")
public class GerenciaTarefas {

    @Id
    @GeneratedValue(strategy = org.springframework.data.annotation.GenerationType.IDENTITY)
    private Long id;
    
    @Field("nome")
    private String nome;
    
    @Field("descricao")
    private String descricao;
    
    @Field("realizada")
    private boolean realizada;
    
    @Field("prioridade")
    private int prioridade;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
}
