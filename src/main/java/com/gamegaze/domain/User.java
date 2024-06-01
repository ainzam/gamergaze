package com.gamegaze.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "First name cannot be blank")
    @Column(name = "firstName")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Column(name = "lastName")
    private String lastName;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Must be a valid email address")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    
    @Column(name = "suspended", nullable = false)
    private boolean suspended = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "suspended_until")
    private Date suspendedUntil;
    
    @Column(name="bio")
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Image bannerImage;

    @OneToOne(cascade = CascadeType.ALL)
    private Image profileImage;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Publication> publications;
    
    @OneToMany(mappedBy = "follower",cascade = CascadeType.ALL)
    private List<Follow> follows;

    @OneToMany(mappedBy = "followed",cascade = CascadeType.ALL)
    private List<Follow> followers;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLike> likes;

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
    

    @Override
    public String getPassword() {
        return password;
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
        return !suspended || (suspendedUntil != null && new Date().after(suspendedUntil));
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
