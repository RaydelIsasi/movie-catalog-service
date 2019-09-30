package raydel.isasi.moviecatalogservice.pojo;

import java.util.List;

public class UserRatings {
	

	private List<Rating> rating;


	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public UserRatings() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRatings(List<Rating> rating) {
		super();
		
		this.rating = rating;
	}

}
