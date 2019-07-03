package com.stephenelf.simpleinterviewtestapp.database;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Character {

    @PrimaryKey
    public long id;
    public String name;
    public String thumbnail;


    @Override
    public boolean equals(@Nullable Object obj) {
        return ((Character)obj).name.toLowerCase().equalsIgnoreCase(name.toLowerCase());
    }
}
