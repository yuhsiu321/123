package org.example.application.Gaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;


import lombok.Getter;

import java.util.Objects;

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

    public boolean winsAgainst(Card card){
        if(Objects.equals(this.getCardType(), "Monster") && Objects.equals(card.getCardType(), "Monster")){
            if(this.getName().contains("Dragon")&&card.getName().contains("Goblin")){
                return true;
            }
            if(this.getName().contains("Wizard")&&card.getName().contains("Ork")){
                return true;
            }
            if(this.getName().contains("FireElf")&&card.getName().contains("Dragon")){
                return true;
            }
        }
        if(Objects.equals(this.getCardType(),"Spell")&&Objects.equals(card.getCardType(),"Monster")){
            if(Objects.equals(this.getElementType(),"Water")&&card.getName().contains("Knight")){
                return true;
            }
        }
        if(Objects.equals(this.getCardType(), "Monster") && Objects.equals(card.getCardType(), "Spell")){
            if(this.getName().contains("Kraken")){
                return true;
            }
        }
        return false;
    }

    public float calculateDamage(Card card){
        // only for spell cards
        if(Objects.equals(this.getCardType(),"Spell")){
            //Effective
            if((Objects.equals(this.getElementType(),"Water")&&Objects.equals(card.getElementType(),"Fire"))||
                    (Objects.equals(this.getElementType(),"Fire")&&Objects.equals(card.getElementType(),"Normal"))||
                    (Objects.equals(this.getElementType(),"Normal")&&Objects.equals(card.getElementType(),"Water"))){
                return this.getDamage()*2;
            }
            //not effective
            if((Objects.equals(this.getElementType(),"Fire")&&Objects.equals(card.getElementType(),"Water"))||
                    (Objects.equals(this.getElementType(),"Normal")&&Objects.equals(card.getElementType(),"Fire"))||
                    (Objects.equals(this.getElementType(),"Water")&&Objects.equals(card.getElementType(),"Normal"))){
                return this.getDamage()/2;
            }
        }
        //no effect
        return this.getDamage();
    }
}
