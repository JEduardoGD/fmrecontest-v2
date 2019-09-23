package mx.fmre.rttycontest.persistence.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "N_ID_USER")
	private Integer id;
	@Column(name = "S_EMAIL")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	@Column(name = "S_PASSWORD")
	//@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;
	@Column(name = "S_NAME")
	@NotEmpty(message = "*Please provide your name")
	private String name;
	@Column(name = "S_LAST_NAME")
	@NotEmpty(message = "*Please provide your last name")
	private String lastName;
	@Column(name = "B_ACTIVE")
	private int active;
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "REL_USER_ROLE", joinColumns = @JoinColumn(name = "N_ID_USER"), inverseJoinColumns = @JoinColumn(name = "N_ID_ROLE"))
	private Set<Role> roles;

}