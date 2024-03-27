package hcmus.alumni.userservice.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "[faculty]")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacultyModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, columnDefinition = "TINYINT")
	private Integer id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
    @Column(name = "description", columnDefinition = "TINYTEXT")
    private String description;
    
	@CreationTimestamp
    @Column(name = "create_at")
    private Date createAt;
	
    @Column(name = "update_at")
    private Date updateAt;
    
	@Column(name = "is_delete", columnDefinition = "TINYINT(1) DEFAULT(0)")
	private Boolean isDelete;
}