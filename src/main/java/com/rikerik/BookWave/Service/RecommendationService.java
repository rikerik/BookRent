package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.Model.Book;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecommendationService {

    public static List<Book> findRecommendedBooks(List<String> topLabels, List<Book> allBooks, List<Book> userBooks) {

        Set<String> userBookTitles = userBooks.stream()
                .map(Book::getTitle)
                .collect(Collectors.toSet());

        // Shuffle the list of all books
        List<Book> shuffledBooks = new ArrayList<>(allBooks);
        Collections.shuffle(shuffledBooks);

        // Find books that are not in the user's library and contain all top labels
        List<Book> recommendedBooks = shuffledBooks.stream()
                .filter(book -> !userBookTitles.contains(book.getTitle())) // Filter out user books
                .filter(book -> bookContainsAllLabels(book, topLabels))
                .limit(3) // Limit to 3 books
                .collect(Collectors.toList());

        if (recommendedBooks.isEmpty()) {
            log.info("No recommended books found for the provided labels.");

            recommendedBooks.add(new Book(" "));
        }

        return recommendedBooks;
    }

    public static List<String> calculateTopLabels(List<Book> userBooks) {
        Map<String, Long> labelFrequencies = userBooks.stream()
                .flatMap(book -> Arrays.stream(book.getLabels().split(",")))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));

        log.info("Label Frequencies: " + labelFrequencies);

        // Sort the labels by frequency in descending order
        List<Map.Entry<String, Long>> sortedLabels = labelFrequencies.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toList());

        // Select the top 3 labels
        return sortedLabels.stream()
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    public static boolean bookContainsAllLabels(Book book, List<String> labels) {
        List<String> bookLabels = Arrays.asList(book.getLabels().split(","));
        return bookLabels.stream().anyMatch(labels::contains);
    }
}
