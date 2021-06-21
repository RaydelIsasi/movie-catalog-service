package raydel.isasi.moviecatalogservice.service;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.*;

import raydel.isasi.moviecatalogservice.pojo.Rating;
import raydel.isasi.moviecatalogservice.pojo.UserRatings;

@Service
public class UserInfoService {

	@Autowired
	RestTemplate rest;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating",
			commandProperties= {
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000"),
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5"),
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="5000")
					
					
			})
	public CompletableFuture<UserRatings> getUserRating(String userid) {
		CompletableFuture<UserRatings> user =  CompletableFuture.supplyAsync(()->rest.getForObject("http://rating-data-service/ratingsdata/users/" + userid,
				UserRatings.class));
		return user;

	}

	public UserRatings getFallbackUserRating(String userid) {
		UserRatings user = new UserRatings();
		user.setRating(Arrays.asList(new Rating("0", 0)));
		return user;

	}

}
