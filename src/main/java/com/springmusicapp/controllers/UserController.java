package com.springmusicapp.controllers;

import com.springmusicapp.model.Musician;
import com.springmusicapp.model.MusicianType;
import com.springmusicapp.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @GetMapping
    public List<Musician> getMusicians() {
        return List.of(
                new Musician(
                        "James",
                        "k@gmail.com",
                        "Jane",
                        EnumSet.of(MusicianType.DRUMMER, MusicianType.GUITARIST)
                ),
                new Musician(
                        "Kamil",
                        "l@gmail.com",
                        "Kamolus",
                        EnumSet.of(MusicianType.DRUMMER, MusicianType.GUITARIST)
                )
        );
    }
}
