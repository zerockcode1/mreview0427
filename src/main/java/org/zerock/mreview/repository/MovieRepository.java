package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.repository.extra.MovieListRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieListRepository {

    @Query("select m, mi , avg(coalesce( r.grade, 0) ), count(r) " +
            "from Movie m " +
            "left join MovieImage mi on mi.movie = m " +
            "left join Review r on r.movie = m " +
            "where mi.profile = true group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi from Movie m left join MovieImage mi " +
            "on mi.movie = m where m.mno = :mno")
    List<Object[]> getMovieWithAll(@Param("mno") Long mno);
}











