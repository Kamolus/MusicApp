package com.springmusicapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "unpopular_bands")
public class UnpopularBand extends Band {

    @Column(nullable = false)
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
