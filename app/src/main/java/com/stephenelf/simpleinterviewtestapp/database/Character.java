package com.stephenelf.simpleinterviewtestapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Character {

    @PrimaryKey
    public long id;
    public String name;
    public String thumbnail;


}
