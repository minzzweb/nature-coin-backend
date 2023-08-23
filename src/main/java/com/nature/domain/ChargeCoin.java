package com.nature.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of="historyNo")
@ToString
@Entity
@Table(name="charge_coin_history")
public class ChargeCoin {
	

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long historyNo;
		
		private Long imageId;
		
		private String imagewriter;

		private int amount;

		@JsonFormat(pattern="yyyy-MM-dd HH:mm")
		@CreationTimestamp
		private LocalDateTime regDate;

		@JsonFormat(pattern="yyyy-MM-dd HH:mm")
		@UpdateTimestamp
		private LocalDateTime updDate;

}
