package com.springmusicapp.model;

import jakarta.persistence.Entity;

@Entity
public class UnpopularBand extends Band {

    private String targetGroup;

    public UnpopularBand(String name, String targetGroup) {
        super(name);
        setTargetGroup(targetGroup);
    }

    public UnpopularBand(PopularBand old) {
        super(old.getName());
        for (Musician m : old.getMembers()) {
            m.updateBand(this);
            this.members.add(m);
        }
        for(Album album : old.getAlbums()){
            album.updateBand(this);
            this.albums.add(album);
        }
        for(Performance p : old.getPerformances()){
            p.updateBand(this);
            this.performances.add(p);
        }
        //dodac usuwanie
    }

    public UnpopularBand() {

    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        if(targetGroup == null || targetGroup.isBlank()){
            throw new IllegalArgumentException("Target group cannot be null or empty");
        }
        this.targetGroup = targetGroup;
    }

}
