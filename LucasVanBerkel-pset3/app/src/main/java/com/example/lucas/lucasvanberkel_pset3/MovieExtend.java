package com.example.lucas.lucasvanberkel_pset3;


public class MovieExtend extends Movie {
    private String reg;
    private String actors;
    private String summary;


    public MovieExtend(String id, String title, String link, String year, String reg, String actors, String summary) {
        super(id, title, link, year);
        this.reg = reg;
        this.actors = actors;
        this.summary = summary;
    }


    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public void setLink(String link) {
        super.setLink(link);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String getLink() {
        return super.getLink();
    }

    @Override
    public String getYear() {
        return super.getYear();
    }

    public String getActors() {
        return actors;
    }

    public String getSummary() {
        return summary;
    }

    public String getReg() {
        return reg;
    }
}
