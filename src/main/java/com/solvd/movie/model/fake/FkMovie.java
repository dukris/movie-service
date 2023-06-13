package com.solvd.movie.model.fake;

import com.solvd.movie.model.Movie;

public class FkMovie extends Movie {

    public FkMovie(){
        super.setId(1L);
        super.setName("Film");
        super.setCountry("Country");
        super.setQualityFrom(360);
        super.setQualityTo(1024);
        super.setGenre("Genre");
        super.setDescription("Description");
        super.setYear(2023);
        super.setLanguage("English");
    }

}
