package raydel.isasi.moviecatalogservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import raydel.isasi.moviecatalogservice.pojo.CatalogItem;
import raydel.isasi.moviecatalogservice.pojo.Movie;
import raydel.isasi.moviecatalogservice.pojo.Rating;

@Service
public class MovieInfoService {
	@Autowired
	RestTemplate rest;
	

	@HystrixCommand(fallbackMethod = "getFallbackCatalog",
			commandProperties= {
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000"),
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5"),
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="5000")
					
					
			})
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = rest.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getMovieName(), movie.getDescription(), rating.getRating());

	}

	public CatalogItem getFallbackCatalog(Rating rating) {

		return new CatalogItem("Movie name not found", "", 0);
	}
}
