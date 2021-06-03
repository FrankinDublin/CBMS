package workbench.Service;

import workbench.Dao.MovieCommentDao;
import workbench.Domain.MovieComment;

import java.util.List;

public class CommentService {
    MovieCommentDao dao = new MovieCommentDao();
    public List<MovieComment> loadComments(String Mid){
        return dao.selectCommentsByMid(Mid);
    }

    public boolean addComment(MovieComment movieComment) {
        return dao.insertComment(movieComment)==1;
    }
}
