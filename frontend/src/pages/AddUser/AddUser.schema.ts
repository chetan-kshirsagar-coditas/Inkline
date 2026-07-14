import z from "zod";
import { ROLE } from "../../types/types";

export const ZRegisterData = z.object({
    email: z.email("Invalid email").trim().nonempty("Required"),
    first_name: z.string("Invalid first name").trim().nonempty("Required").min(2, "First name should be at least of 2 chars.").max(30, "First name can't be longer than 30 chars."),
    last_name: z.string("Invalid last name").trim().nonempty("Required").min(2, "last name should be at least of 2 chars.").max(30, "last name can't be longer than 30 chars."),
    role: z.enum([ROLE.ADMIN, ROLE.AUTHOR, ROLE.EDITOR])
})