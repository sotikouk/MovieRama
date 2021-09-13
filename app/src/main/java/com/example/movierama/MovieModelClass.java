package com.example.movierama;

public class MovieModelClass {

    String id;
    String name;
    String img;
    String releaseD;
    String overview;
    boolean favorite = false;

    public MovieModelClass(String id, String name, String img, String releaseD, String overview) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.releaseD = releaseD;
        this.overview = overview;
        this.favorite = favorite;
    }

    public MovieModelClass() {
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getOverview() {
        return overview;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getReleaseD() {return releaseD;}

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setReleaseD(String releaseD) {
        this.releaseD = releaseD;
    }
}
