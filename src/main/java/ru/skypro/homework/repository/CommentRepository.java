package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Collection<Comment> findCommentsByAd_Pk(int AdId);

    void deleteCommentByAd_PkAndPk(int adId, int commentId);
}
