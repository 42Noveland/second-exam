package com.example.mediaplayer;

public class Article {
    public int Id;
    public String name;
    public String description;
    public String link = "http://49.234.117.142:3000playlist/detail?id=2154736";
    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public int getId(){return Id;}

    public void setId(String time){this.Id = Id;}

    public String getDescription(){return description;}

    public void setDescription(String shareUser){this.description = description;}

    public void setLink(){ this.link = "http://49.234.117.142:3000playlist/detail?id=2154736";}

    public String getLink(){return link;}
}
