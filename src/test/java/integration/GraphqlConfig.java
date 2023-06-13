package integration;

import com.solvd.movie.model.Movie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.model.fake.FkMovie;
import com.solvd.movie.service.MovieService;
import com.solvd.movie.service.impl.MovieServiceImpl;
import com.solvd.movie.web.config.WebConfig;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@TestConfiguration
@EnableAutoConfiguration
@Import(WebConfig.class)
@SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass")
public class GraphqlConfig {

    @Bean
    public MovieService movieService() {
        MovieService movieService = Mockito.mock(MovieServiceImpl.class);
        Mockito.when(movieService.create(Mockito.any(Movie.class)))
                .thenReturn(Mono.just(new FkMovie()));
        Mockito.when(movieService.update(Mockito.any(Movie.class)))
                .thenReturn(Mono.just(new FkMovie()));
        Mockito.when(movieService.delete(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        Mockito.when(movieService.retrieveAllByCriteria(
                        Mockito.any(SearchCriteria.class),
                        Mockito.any(Pageable.class)
                )
        ).thenReturn(Flux.just(new FkMovie()));
        Mockito.when(movieService.retrieveById(Mockito.anyLong()))
                .thenReturn(Mono.just(new FkMovie()));
        return movieService;
    }

}