package com.rikerik.BookWave.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents the composite key for the BookUser entity.
 * It consists of the book ID and the user ID.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUserId implements Serializable {

    private Long book;
    private Long user;
}
