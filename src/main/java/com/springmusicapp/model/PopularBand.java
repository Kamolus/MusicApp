package com.springmusicapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class PopularBand extends Band {

    @OneToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    private double earnedMoney;

    public PopularBand() {

    }
    public PopularBand(String name) {
        super(name);
    }

    public PopularBand(UnpopularBand old) {
        super(old.getName());
        for (Musician m : old.getMembers()){
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
        if(contract != null){
            contract.removeContract();
        }
        //dodac usuwanie starego obiektu
    }

    public void addContract(Contract contract) {
        if (this.contract == null) {
            this.contract = contract;
        }
    }

    protected Contract getContract() {
        return contract;
    }

    public void removeContract() {
        this.contract = null;
    }
}
