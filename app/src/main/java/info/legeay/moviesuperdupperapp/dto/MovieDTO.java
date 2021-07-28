package info.legeay.moviesuperdupperapp.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import info.legeay.moviesuperdupperapp.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MovieDTO implements Serializable {

    private String Title;
    private String Released;
    private String Genre;
    private String Director;
    private String Plot;
    private String Actors;
    private String Awards;
    private String Poster;

    public Movie toMovie() {
        return new Movie(Title, Released, Genre, Director, Plot, Actors, Awards, Poster);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "Title='" + Title + '\'' +
                ", Released='" + Released + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Director='" + Director + '\'' +
                ", Plot='" + Plot + '\'' +
                ", Actors='" + Actors + '\'' +
                ", Awards='" + Awards + '\'' +
                ", Poster='" + Poster + '\'' +
                '}';
    }
}
