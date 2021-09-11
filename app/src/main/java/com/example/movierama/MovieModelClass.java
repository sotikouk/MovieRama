package com.example.movierama;

public class MovieModelClass {

    String id;
    String name;
    String img;
    String releaseD;
    String overview;

    public MovieModelClass(String id, String name, String img, String releaseD, String overview) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.releaseD = releaseD;
        this.overview = overview;
    }

    public MovieModelClass() {
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
