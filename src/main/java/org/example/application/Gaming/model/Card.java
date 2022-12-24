package org.example.application.Gaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.example.application.Gaming.type.CardType;
import org.example.application.Gaming.type.ElementType;

import lombok.Getter;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Card {



    private String Id;

    private String Name;

    private float Damage;

    private ElementType elementType;


    private CardType cardType;


    private boolean isLocked;
    public Card(String Id, String Name, float Damage) {
        this.Id = Id;
        this.Name = Name;
        this.Damage = Damage;
    }


    public boolean lock(boolean isLocked) {
        return isLocked;
    }

    public Card(){}


    public String getId(){return Id;}

    public void setId(String Id){this.Id = Id;}

    public String getName(){return Name;}

    public void setName(String Name){this.Name = Name;}

    public float getDamage(){return Damage;}

    public void setDamage(float Damage){this.Damage = Damage;}

}
