package org.zerock.mreview.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReviewRepositoryTests {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void insertReviews(){

        IntStream.rangeClosed(1,200).forEach(i-> {

            Long mno = (long)(Math.random()* 100) + 1;

            Long mid = (long)(Math.random()* 100) + 1;

            Movie movie = Movie.builder().mno(mno).build();
            Member member = Member.builder().mid(mid).build();

            int grade = (int)(Math.random() * 5) +1;

            //Review객체 생성
            Review review = Review.builder().member(member).movie(movie)
                    .grade(grade).text("이 영화에 대한 느낌은...").build();

            reviewRepository.save(review);

        });

    }
    @Test

    public void testFindByMovie() {
        Long mno = 200L;
        Movie movie = Movie.builder().mno(mno).build();

        reviewRepository.findByMovie(movie).forEach(r -> {
            log.info(r);
            log.info(r.getMember());
        });
    }

    @Test
    public void testFindWithMovie() {
        Long mno = 200L;

        reviewRepository.findWithMovie(mno).forEach(r -> log.info(r));
    }
}












