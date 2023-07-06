package com.rikerik.BookWave.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {
    private long bookId;
    private String title;
    private String description;
    private String authorName;
    private boolean rented;
    private String imageBase64;
}
