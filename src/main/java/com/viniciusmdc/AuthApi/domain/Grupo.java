package com.viniciusmdc.AuthApi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@Table(name = "tb_grupo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_grupo")
    @SequenceGenerator(name="seq_grupo", sequenceName="seq_grupo", allocationSize = 1)
    private Long id;

    private String nome;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "tb_usuario_grupo", joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    private List<Usuario> usuarios;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "tb_grupo_funcao", joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_funcao"))
    private List<Funcao> funcoes;

}
