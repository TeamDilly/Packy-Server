package com.dilly.global.fixture;

import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;

public class DtoFixture {

    public static BoxImgResponse boxImgResponse(Long id, Long sequence) {
        return BoxImgResponse.builder()
            .id(id)
            .sequence(sequence)
            .boxNormal("www.test" + id + ".com")
            .boxSmall("www.test" + id + ".com")
            .boxSet("www.test" + id + ".com")
            .boxTop("www.test" + id + ".com")
            .boxLottie("www.test" + id + ".com")
            .build();
    }

    public static ImgResponse imgResponse(Long id, Long sequence) {
        return ImgResponse.builder()
            .id(id)
            .sequence(sequence)
            .imgUrl("www.test" + id + ".com")
            .build();
    }
}
