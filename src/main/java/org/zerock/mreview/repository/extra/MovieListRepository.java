package org.zerock.mreview.repository.extra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieListRepository {


    Page<Object[]> getListWithAllImages(Pageable pageable);
}
