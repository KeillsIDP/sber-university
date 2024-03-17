package me.keills.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name="recipes")
public class Recipe {
    @Id
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private Map<String,String> ingredients;
}
