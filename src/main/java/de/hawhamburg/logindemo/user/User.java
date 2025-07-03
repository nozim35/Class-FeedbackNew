package de.hawhamburg.logindemo.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * {@link User} must implement {@link UserDetails} and {@link CredentialsContainer} so that
 * it can be used for logins by Spring.
 */
@Table("USER_ACCOUNT")
public class User implements UserDetails, CredentialsContainer {

    @Id
    private long id;

    private String name;

    private String password;

    private String email;

    private Set<Authority> authorities = new HashSet<>();

    public User() {
    }

    public User(String name, String password) {
        this(name, password, null, Role.USER);
    }

    public User(String name, String password, String email, String... authorities) {
        this(name, password, email, Arrays.asList(authorities));
    }

    public User(String name, String password, String email, Collection<String> authorities) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.authorities.addAll(convert(authorities));
    }

    private static Collection<Authority> convert(Collection<String> strings) {
        return strings.stream().map(Authority::new).toList();
    }

    long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean addAuthority(String authority) {
        return authorities.add(new Authority(authority));
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return convertToGrantedAuthorities(authorities);
    }

    private static List<SimpleGrantedAuthority> convertToGrantedAuthorities(Collection<Authority> authorities) {
        var result = new ArrayList<SimpleGrantedAuthority>();
        for (var authority : authorities) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority.role());
            result.add(simpleGrantedAuthority);
        }
        return result;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}