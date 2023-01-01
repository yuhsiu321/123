package org.example.application.Gaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;


import lombok.Getter;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Card {



    private String Id;

    private String Name;

    private float Damage;

    private String elementType;


    private String cardType;


    private boolean isLocked;
    public Card(String Id, String Name, float Damage) {
        this.Id = Id;
        this.Name = Name;
        this.Damage = Damage;
    }

    public void setLock(boolean isLocked){this.isLocked = isLocked;}

    public boolean lock() {
        return isLocked;
    }

    public Card(){}


    public String getId(){return Id;}

    public void setId(String Id){this.Id = Id;}

    public String getName(){return Name;}

    public void setName(String Name){this.Name = Name;}

    public float getDamage(){return Damage;}

    public void setDamage(float Damage){this.Damage = Damage;}

    @Override
    public String toString() {
        return "Card [id=" + Id + ", name=" + Name + ", Damage="+Damage+" Card_type="+cardType+ "]";
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String Name) {
        if (Name.contains("Water")){
            this.elementType = "Water";
        }else if(Name.contains("Fire")){
            this.elementType = "Fire";
        } else if (Name.contains("Regular")) {
            this.elementType = "Normal";
        }else{
            this.elementType = "Normal";
        }
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String Name) {
        if (Name.contains("Spell")){
            this.cardType = "Spell";
        }else {
            this.cardType = "Monster";
        }
    }
}
