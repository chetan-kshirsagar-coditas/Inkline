import type z from "zod"
import type { ZLoginData } from "./LoginForm.schema"

// export interface LoginFormData {
//     email: string
//     otp: string
// }


export type LoginFormData = z.input<typeof ZLoginData>