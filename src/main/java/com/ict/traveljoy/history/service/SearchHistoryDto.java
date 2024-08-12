package com.ict.traveljoy.history.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.history.repository.SearchHistory;
import com.ict.traveljoy.users.repository.Users;

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
public class SearchHistoryDto {

	private Long id;
	private Users user;
	private String searchWord;
	private LocalDateTime searchDate;
	private Boolean isActive;
	private Boolean isDelete;
	private LocalDateTime deleteDate;
	
	public SearchHistory toEntity() {
		return SearchHistory.builder()
				.id(id)
				.user(user)
				.searchWord(searchWord)
				.searchDate(searchDate)
				.isActive(isActive == true ? 1 : 0)
				.isDelete(isDelete == true ? 1 : 0)
				.build();
	}
	
	public static SearchHistoryDto toDTO(SearchHistory searchHistory) {
		return SearchHistoryDto.builder()
				.id(searchHistory.getId())
				.user(searchHistory.getUser())
				.searchWord(searchHistory.getSearchWord())
				.searchDate(searchHistory.getSearchDate())
				.isActive(searchHistory.getIsActive() == 1 ? true : false)
				.isDelete(searchHistory.getIsDelete() == 1 ? true : false)
				.build();
	}
}
