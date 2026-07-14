package com.mukesh.inkLine.dto.request;

import java.util.UUID;

public record RejectDraftRequestDTO(
        UUID draftId,
        String reasonOfRejection
) {
}
