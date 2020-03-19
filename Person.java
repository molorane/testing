package org.dclm.theSeal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "person")
public class Person {

	@Id
	private String idno;

	@NotEmpty(message = "last name Required.")
	private String lastName;

	@NotEmpty(message = "first name Required.")
	private String firstName;

	private String otherName;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String profile;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;

	@JsonBackReference
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountId")
	private Account account;

	private String aboutMe;

}
