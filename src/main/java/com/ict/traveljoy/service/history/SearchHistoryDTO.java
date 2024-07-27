package com.ict.traveljoy.service.history;

import java.time.LocalDateTime;

import com.ict.traveljoy.repository.history.SearchHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistoryDTO {

	private long id;
	private long userId;
	private String searchWord;
	private LocalDateTime searchDate;
	private boolean isActive;
	private boolean isDelete;
	private LocalDateTime deleteDate;
	
	public SearchHistory toEntity() {
		return SearchHistory.builder()
				.id(id)
				.build();
	}
	
	public static SearchHistoryDTO toDTO(SearchHistory history) {
		return SearchHistoryDTO.builder()
				.id(history.getId())
				.build();
	}
}
