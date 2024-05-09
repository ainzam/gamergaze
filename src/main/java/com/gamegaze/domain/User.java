package com.gamegaze.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ResourceUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "firstName")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(name = "lastName")
    private String lastName;
    
    @NotBlank(message = "El username no puede estar vacío")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Debe ser una dirección de correo electrónico válida")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Image bannerImage;

    @OneToOne(cascade = CascadeType.ALL)
    private Image profileImage;
    
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Publication> publications;
    

	public User(String firstName, String lastName, String email, String password, UserRole role, String username) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
    

    @Transient
    public Image getProfileImage() {
        if (profileImage != null && profileImage.getData() != null) {
            return profileImage;
        } else {
            return getDefaultProfileImage();
        }
    }

    private Image getDefaultProfileImage() {
        Image defaultProfileImage = new Image();
        try {
            Path defaultImagePath = ResourceUtils.getFile("classpath:static/assets/logo-vt.svg").toPath();
            byte[] imageData = Files.readAllBytes(defaultImagePath);
            defaultProfileImage.setData(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultProfileImage;
    }


}
