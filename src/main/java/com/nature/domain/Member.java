package com.nature.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(value="hibernateLazyInitializer")
@Getter
@Setter
@EqualsAndHashCode(of="userNo")
@ToString
@Entity
@Table(name="member")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no")
	private Long userNo;
	
	
	@NotBlank
	@Column(nullable = false, length = 30, unique = true)
	private String email;
	
	@NotBlank
	@Column(length = 100, nullable = false)
	private String nickname;
	
	@NotBlank
	@Column(length = 200, nullable = false)
	private String password;
	
	@Transient
	private MultipartFile picture;
	
	@Column(length = 255)
	private String pictureUrl;

	private int coin;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private LocalDateTime regDate;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "user_no")
	private List<MemberAuth> authList = new ArrayList<MemberAuth>();
	
	
	public void addAuth(MemberAuth auth) {
		authList.add(auth);
	}
}
