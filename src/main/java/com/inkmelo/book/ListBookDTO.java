package com.inkmelo.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListBookDTO{
	private Integer id;
    private String title;
    private String bookCoverImg;
    private String ISBN;
}
