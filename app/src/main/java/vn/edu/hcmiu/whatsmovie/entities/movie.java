package vn.edu.hcmiu.whatsmovie.entities;

/**
 * Created by quyvu-pc on 30/12/2015.
 */
public class movie {

    private String Title;
    private String Year;
    private String Genre;
    private String Director;
    private String Actor;
    private String Plot;
    private String Poster;
    private String IMDBRating;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActor() {
        return Actor;
    }

    public void setActor(String actor) {
        Actor = actor;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getIMDBRating() {
        return IMDBRating;
    }

    public void setIMDBRating(String IMDBRating) {
        this.IMDBRating = IMDBRating;
    }
}
