package com.inkmelo.bookrating;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRatingStatsDTO {
    private Integer bookId;
    private Double averageStar;
    private Long totalComments;
}