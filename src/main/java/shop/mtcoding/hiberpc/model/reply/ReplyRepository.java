package shop.mtcoding.hiberpc.model.reply;

import org.springframework.stereotype.Repository;
import shop.mtcoding.hiberpc.model.MyRepository;

import javax.persistence.EntityManager;

@Repository
public class ReplyRepository extends MyRepository<Reply> {

    public ReplyRepository(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<Reply> getEntityClass() {
        return Reply.class;
    }

    @Override
    protected String getEntityName() {
        return "Reply";
    }
}
