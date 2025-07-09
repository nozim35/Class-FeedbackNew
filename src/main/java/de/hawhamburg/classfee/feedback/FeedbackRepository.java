package de.hawhamburg.classfee.feedback;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

    List<Feedback> findTop5ByOrderByCreatedAtDesc();
    List<Feedback> findByModuleName(String moduleName);

}
