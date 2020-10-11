package com.ex.dao;

import com.ex.model.Person;
import com.ex.model.Player;

import java.util.List;

public interface PersonDAO {
    /* Add a person(coach) to the dbase */
    public void addPerson(Person person)throws Exception;

    /* add a player to the dbase */
    public void addPlayer(Player player)throws Exception;

    /* Gets list of all players */
    public List<Player> getAllPlayers();

    /* Gets a list of all coaches */
    public List<Person> getAllCoaches();
}
