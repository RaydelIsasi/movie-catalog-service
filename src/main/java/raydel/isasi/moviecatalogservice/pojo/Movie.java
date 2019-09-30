package raydel.isasi.moviecatalogservice.pojo;

public class Movie {

	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String MovieId;

	private String movieName;

	public String getMovieId() {
		return MovieId;
	}

	public void setMovieId(String movieId) {
		MovieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Movie(String movieId, String movieName) {
		super();
		MovieId = movieId;
		this.movieName = movieName;
	}
}
