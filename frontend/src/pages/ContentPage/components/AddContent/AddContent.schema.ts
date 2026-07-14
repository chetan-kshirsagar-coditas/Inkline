import z from "zod";


export const ZContentStatus = z.enum([
    "DRAFT",
    "UNDER_AI_REVIEW",
    "UNDER_EDITOR_REVIEW",
    "CHANGES_REQUESTED",
    "APPROVED",
    "PUBLISHED"
]);

export const ZAddContent = z.object({
    title: z.string("Invalid title").trim().nonempty("Required"),
    body: z.string("Invalid body").trim().nonempty("Required"),
    category: z.string("Invalid category").trim().nonempty("Required").optional(),
    file: z.file("Invalid file").optional()
})