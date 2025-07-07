package com.springmusicapp.model;

import java.util.Optional;


public interface IScout {
    void setPhoneNumber(String phoneNumber);
    String getPhoneNumber();
//    default void findAndAssignMusicianToBand(Band band, MusicianType desiredType){
//        Optional<Musician> musician = ObjectExtent
//                .getExtentFromClass(Musician.class).stream()
//                .filter(m -> m.isAvailable() && m.hasType(desiredType))
//                .findFirst();
//
//        musician.ifPresentOrElse(
//                m -> {
//                    band.addMusician(m);
//                    System.out.println("Dodano muzyka: " + m.getName() + " do zespołu: " + band.getName());
//                },
//                () -> System.out.println("Brak dostępnych muzyków dla typu: " + desiredType)
//        );
//    }
//    default void displayPopularBands(){
//        ObjectExtent.getExtentFromClass(PopularBand.class);
//    }
}
