package goit.edu.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto{
    private Long id;
    private String title;
    private String content;

    public NoteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
