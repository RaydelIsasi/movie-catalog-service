package raydel.isasi.moviecatalogservice.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.util.UriBuilderFactory;

import raydel.isasi.moviecatalogservice.pojo.CatalogItem;
import raydel.isasi.moviecatalogservice.pojo.Movie;
import raydel.isasi.moviecatalogservice.pojo.Rating;
import raydel.isasi.moviecatalogservice.pojo.UserRatings;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	RestTemplate rest;
	@Autowired
	WebClient.Builder webclientbuilder;

	@PostMapping("/{userid}")
	public List<CatalogItem> getCatalog(@PathVariable("userid") String userid) {

		// get all rated movies id
		UserRatings userratings = rest.getForObject("http://localhost:8082/ratingsdata/users/"+userid, UserRatings.class);
		// for each movie id , call movie info service and get details
		return userratings.getRating().stream().map(rating -> {
			// for each movie id , call movie info service and get details
			Movie movie = rest.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);

			// put them all together

			return new CatalogItem(movie.getMovieName(), "SCIFI", rating.getRating());
		}

		).collect(Collectors.toList());

		// asyncronous call
		// Movie movie =
		// webclientbuilder.build().get().uri("http://localhost:8081/movies/" +
		// rating.getMovieId())
		// .retrieve().bodyToMono(Movie.class).block();
	}
}
