package com.springmusicapp.domain.event;

import com.springmusicapp.domain.band.Band;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "performances")
public class Performance{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
