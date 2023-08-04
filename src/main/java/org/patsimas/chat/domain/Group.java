package org.patsimas.chat.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="group_name")
    private String groupName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by",referencedColumnName = "id")
    private User createdByUser;
}
