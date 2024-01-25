package br.edu.ifpb.agora.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @EmbeddedId
    private AuthorityId id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User username;
    
    @Column(name = "authority", insertable = false, updatable = false)
    private String authority;

    public Authority(User user, String authority) {
        Authority.AuthorityId id = new Authority.AuthorityId(user.getUsername(), authority);
        this.id = id;
        this.username = user;
        this.authority = authority;


    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorityId implements Serializable {
        private String username;
        private String authority;
    }
    
}
