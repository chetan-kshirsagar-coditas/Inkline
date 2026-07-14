import type z from "zod";
import type { ZRegisterData } from "./AddUser.schema";

export type RegisterData = z.infer<typeof ZRegisterData>;