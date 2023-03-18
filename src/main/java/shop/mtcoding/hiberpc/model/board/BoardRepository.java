package shop.mtcoding.hiberpc.model.board;

import org.springframework.stereotype.Repository;
import shop.mtcoding.hiberpc.model.MyRepository;

import javax.persistence.EntityManager;

@Repository
public class BoardRepository extends MyRepository<Board> {

    public BoardRepository(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<Board> getEntityClass() {
        return Board.class;
    }

    @Override
    protected String getEntityName() {
        return "Board";
    }
}
