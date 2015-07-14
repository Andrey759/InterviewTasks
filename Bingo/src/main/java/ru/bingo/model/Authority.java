package ru.bingo.model;

import javax.persistence.*;

@Entity
@Table(name = "authorities", schema = "", catalog = "test")
public class Authority {

    private int id;
    private User user;
    private String role;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "role", nullable = false, insertable = true, updatable = true, length=250)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
