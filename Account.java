package org.dclm.theSeal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
	@NotEmpty(message = "Username required.")
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(unique = true)
    @NotEmpty(message = "Email Required.")
    @Email(message = "Invalid email format")
	private String email;

	@Column(nullable = false)
	@Size(min = 4, message = "Password must at least be 4 characters")
	private String password;
	
	@Transient
	@Size(min = 4, message = "Password must at least be 4 characters")
	private String confirmPassword;
	
	@Column(columnDefinition = "int default 1")
	private int status;

	private boolean isLocked;

	private boolean isActive;

	private LocalDateTime createdDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expiryDate;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "account_role",
			joinColumns = @JoinColumn(name = "account_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@JsonBackReference
	@OneToOne(mappedBy = "account", fetch = FetchType.EAGER)
	private Person person;

	@PrePersist
	public void prePersist() {
		this.expiryDate = LocalDate.now().plusMonths(1);
		this.isActive = true;
		this.isLocked = false;
		this.status = 1;
	}

	public boolean accountExpired() {
		return !expiryDate.isAfter(LocalDate.now());
	}

	public boolean hasRole(String userRole){
		return (roles.stream()
				.filter(role -> userRole.equals(role.getName()))
				.findAny()
				.orElse(null) != null);
	}

}
