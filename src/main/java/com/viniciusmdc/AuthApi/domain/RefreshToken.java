package com.viniciusmdc.AuthApi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Table(name = "tb_refresh_token")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(name="id", insertable = true, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "expiracao")
    private Date expiracao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


}
