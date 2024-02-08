package com.dilly.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FileRequest(
	@Schema(example = "https://examplebucket.s3.amazonaws.com /test.txt?\n"
		+ "X-Amz-Algorithm=AWS4-HMAC-SHA256\n"
		+ "&X-Amz-Credential=<YOUR_ACCESS_KEY_ID>/20160115/ap-northeast-2/s3/aws4_request&\n"
		+ "&X-Amz-Date=20160115T000000Z\n"
		+ "&X-Amz-Expires=86400\n"
		+ "&X-Amz-SignedHeaders=host\n"
		+ "&X-Amz-Signature=<SIGNATURE_VALUE>")
	String url
) {
}
