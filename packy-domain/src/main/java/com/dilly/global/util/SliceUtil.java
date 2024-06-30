package com.dilly.global.util;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SliceUtil {

    public static <T> Slice<T> checkLastPage(Pageable pageable, List<T> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            results.remove(results.size() - 1);
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
