package info.legeay.moviesuperdupperapp.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    private String title;
    private String releaseDateString;
    private String types;
    private String director;
    private String description;
    private String actors;
    private String awards;
    private String imageUrl;
}
