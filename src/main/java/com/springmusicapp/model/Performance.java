package com.springmusicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "performances")
public class Performance{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @NotNull
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id")
    @NotNull
    private Band band;

    private boolean isMainBand;

    public Performance(Event event, Band band, boolean isMainBand) {
        setEvent(event);
        setBand(band);
        this.isMainBand = isMainBand;
        event.addPerformance(this);
        band.addPerformance(this);
    }

    public Performance(){

    }

    public void setEvent(Event event) {
        if(event == null) {
            throw new NullPointerException("Event cannot be null");
        }
        this.event = event;
    }


    public void setBand(Band band) {
        if(band == null || band.equals(this.band)) {
            throw new NullPointerException("band is null");
        }
        this.band = band;
    }

    protected void updateBand(Band band) {
        this.band = band;
    }

    public boolean isMainBand() {
        return isMainBand;
    }

    public void setMainBand(boolean mainBand) {
        isMainBand = mainBand;
    }

    public void removePerformance() {
        if(this.event != null) {
            this.event.removePerformance(this);
        }
    }

    @Override
    public String toString() {
        return "Performance{" +
                "event=" + event.getName() +
                ", band=" + band.getName() +
                ", isMainBand=" + isMainBand +
                '}';
    }
}
