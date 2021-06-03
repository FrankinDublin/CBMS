package workbench.Service;

import workbench.Dao.MovieDao;
import workbench.Domain.Condition;
import workbench.Domain.Movie;

import java.util.List;

public class UserIntService {
    private MovieDao movieDao=new MovieDao();
    public List<Movie> loadMovies(Condition condition, int sortMark){
        int count=0;
        if (condition.getName()!="")
            count+=1;
        if (condition.getProducer()!="")
            count+=1;
        if (condition.getShowTime()!="")
            count+=1;
        if (condition.getMovieType()!="")
            count+=1;
        return movieDao.selectAllMovies(condition,count,sortMark);
    }
    public Movie ShowMovieDetail(String id){
        return movieDao.selectMovieById(id);
    }
}
