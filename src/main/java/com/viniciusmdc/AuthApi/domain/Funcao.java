package com.viniciusmdc.AuthApi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Table(name = "tb_funcao")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_funcao")
    @SequenceGenerator(name="seq_funcao", sequenceName="seq_funcao", allocationSize = 1)
    private Integer id;

    private String descricao;

    private String metodo;

    private String url;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "tb_grupo_funcao", joinColumns = @JoinColumn(name = "id_funcao"),
            inverseJoinColumns = @JoinColumn(name = "id_grupo"))
    private List<Grupo> grupos;


}
