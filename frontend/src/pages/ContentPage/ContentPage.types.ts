import type z from "zod";
import type { ZContentStatus } from "./components/AddContent/AddContent.schema";

export interface Content {
    "id": string,
    "title": string,
    "body": string,
    "category": string,
    "createdAt": string,
    "submittedAt": string,
    "contentStatus": z.infer<typeof ZContentStatus>
}

export type ContentPageModalState = { type: "ADD_CONTENT" } | null;