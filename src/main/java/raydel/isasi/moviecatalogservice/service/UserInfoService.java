package raydel.isasi.moviecatalogservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import raydel.isasi.moviecatalogservice.pojo.Rating;
import raydel.isasi.moviecatalogservice.pojo.UserRatings;

@Service
public class UserInfoService {

	@Autowired
	RestTemplate rest;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	public UserRatings getUserRating(String userid) {
		UserRatings user = rest.getForObject("http://rating-data-service/ratingsdata/users/" + userid,
				UserRatings.class);
		return user;

	}

	public UserRatings getFallbackUserRating(String userid) {
		UserRatings user = new UserRatings();
		user.setRating(Arrays.asList(new Rating("0", 0)));
		return user;

	}

}
