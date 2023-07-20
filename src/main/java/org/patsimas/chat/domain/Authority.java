package org.patsimas.chat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.patsimas.chat.enums.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {

	@Id
	@Column(name = "id")
	private Short id;

	@Enumerated(EnumType.STRING)
	@Column(name = "description")
	private Role description;
}
