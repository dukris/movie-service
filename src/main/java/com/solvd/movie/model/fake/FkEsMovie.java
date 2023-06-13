package com.solvd.movie.model.fake;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Quality;

public class FkEsMovie extends EsMovie {

    public FkEsMovie() {
        super.setId(1L);
        super.setName("Film");
        super.setCountry("Country");
        super.setGenre("Genre");
        super.setYear(2023);
        super.setLanguage("English");
        Quality quality = new Quality();
        quality.setFrom(360);
        quality.setTo(1024);
        super.setQuality(quality);
    }

}
