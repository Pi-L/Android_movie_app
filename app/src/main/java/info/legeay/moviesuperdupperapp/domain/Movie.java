package info.legeay.moviesuperdupperapp.domain;

import java.util.List;

public class Movie {

    private String title;
    private String releaseDateString;
    private String types;
    private String director;
    private String description;
    private String actors;
    private String awards;
    private String imageUrl;

    public Movie(String title, String releaseDateString, String types, String director, String description, String actors, String awards, String imageUrl) {
        this.title = title;
        this.releaseDateString = releaseDateString;
        this.types = types;
        this.director = director;
        this.description = description;
        this.actors = actors;
        this.awards = awards;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDateString() {
        return releaseDateString;
    }

    public void setReleaseDateString(String releaseDateString) {
        this.releaseDateString = releaseDateString;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
