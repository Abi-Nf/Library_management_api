package models;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    private Long id;
    private String bookName;
    private Integer pageNumbers;
    private String topic;
    private LocalDate releaseDate;
    private Author author;
}
