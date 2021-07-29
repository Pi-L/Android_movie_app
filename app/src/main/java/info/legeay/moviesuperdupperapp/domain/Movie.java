package info.legeay.moviesuperdupperapp.domain;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable {

    private String title;
    private String releaseDateString;
    private String yearRelease;
    private String types;
    private String director;
    private String description;
    private String actors;
    private String awards;
    private String imageUrl;
    private String imdbID;

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseDateString='" + releaseDateString + '\'' +
                ", yearRelease='" + yearRelease + '\'' +
                ", types='" + types + '\'' +
                ", director='" + director + '\'' +
                ", description='" + description + '\'' +
                ", actors='" + actors + '\'' +
                ", awards='" + awards + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imdbID='" + imdbID + '\'' +
                '}';
    }
}
