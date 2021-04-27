package org.zerock.mreview.repository.extra;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.entity.QMovie;
import org.zerock.mreview.entity.QMovieImage;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class MovieListRepositoryImpl extends QuerydslRepositorySupport implements MovieListRepository {

    public MovieListRepositoryImpl() {
        super(Movie.class);
    }

    @Override
    public Page<Object[]> getListWithAllImages(Pageable pageable) {

        log.info("------------------------------------------");
        log.info("getListWithAllImages");
        log.info("------------------------------------------");

        QMovie movie = QMovie.movie;

        OrderSpecifier<Long> order = new OrderSpecifier<Long>(Order.DESC, new PathBuilder(Movie.class, "mno"));


        JPQLQuery<Movie> queryInner = from(movie).orderBy(order).offset(pageable.getOffset()).limit(pageable.getPageSize());


        log.info("===========================");
        log.info(queryInner);

        List<Movie> movieList = queryInner.fetch();

        long count = queryInner.fetchCount();;

        log.info(movieList);

        QMovieImage movieImage = QMovieImage.movieImage;

        JPQLQuery<Tuple> tuple = from(movie)
                .leftJoin(movieImage).on(movieImage.movie.eq(movie))
                .select(movie, movieImage).where(movie.in(movieList))
                //.select(movie, movieImage).where(movie.in(queryInner))
                .orderBy(order);

        List<Tuple> result = tuple.fetch();

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable, count);
    }
}
