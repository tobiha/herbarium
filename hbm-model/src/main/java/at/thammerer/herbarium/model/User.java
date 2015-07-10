package at.thammerer.herbarium.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The User JPA entity.
 */
@Entity
@Table(name = "user")
@NamedQueries({
	@NamedQuery(
		name = User.FIND_BY_USERNAME,
		query = "select u from User u where username = :username"
	)
})
public class User extends AbstractEntity {

	public static final String FIND_BY_USERNAME = "user.findByUserName";

	@NotNull
	@Column(unique = true, nullable = false)
	private String username;

	@NotNull
	@Column(name = "password_digest", nullable = false)
	private String passwordDigest;

	private String email;

	public User() {

	}

	public User(String username, String passwordDigest, String email) {
		this.username = username;
		this.passwordDigest = passwordDigest;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordDigest() {
		return passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "User{" +
			"username='" + username + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
