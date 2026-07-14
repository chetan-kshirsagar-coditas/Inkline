import type z from "zod"
import type { ZAddContent } from "./AddContent.schema"

export interface AddContentProps {
    onClose: () => void
}

export type AddContentData = z.infer<typeof ZAddContent>