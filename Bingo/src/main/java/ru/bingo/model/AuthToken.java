package ru.bingo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.bingo.utils.ChiperUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthToken {

    private String login;
    private String pass;
    private List<GrantedAuthority> authorities;

    public AuthToken() {
    }

    public AuthToken(User user) {
        this.setLogin(user.getUsername());
        this.setPass(user.getPass());
        this.setAuthorities(user.getAuthorities());
    }

    public AuthToken(String cookie) throws Exception {
        String str = ChiperUtils.decrypt(cookie);
        int separator = str.lastIndexOf(":");
        if (separator > 0) {
            this.setLogin(str.substring(0, separator));
            List<GrantedAuthority> authorities = new ArrayList<>();
            String[] authParts = str.substring(separator + 1).split(",");
            for (String authPart : authParts) {
                authorities.add(new SimpleGrantedAuthority(authPart));
            }
            this.setAuthorities(authorities);
        } else
            throw new IllegalArgumentException("Token must contains ':'");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        List<GrantedAuthority> list = new ArrayList<>();
        authorities
                .stream()
                .map(Authority::getRole)
                .forEach(e -> list.add(new SimpleGrantedAuthority(e)));
        this.setAuthorities(list);
    }

    public String generateCookie() throws Exception {
        return ChiperUtils.encrypt(
                this.getLogin() + ":" +
                    String.join(",",
                            this.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList())
                    )
        );
    }

}
