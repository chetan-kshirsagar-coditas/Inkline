
export type ContentStatus =
    "DRAFT" |
    "UNDER_AI_REVIEW" |
    "UNDER_EDITOR_REVIEW" |
    "CHANGES_REQUESTED" |
    "APPROVED" |
    "PUBLISHED"

export interface Content {
    "id": string,
    "title": string,
    "body": string,
    "category": string,
    "createdAt": string,
    "submittedAt": string,
    "contentStatus": ContentStatus
}

export type ContentPageModalState = { type: "ADD_CONTENT" } | null;