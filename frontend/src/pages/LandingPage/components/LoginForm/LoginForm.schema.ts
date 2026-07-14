import z from "zod";

export const ZLoginData = z.object({
    email: z.email("Invalid email").trim().nonempty("Required"),
    otp: z.coerce.number("Invalid OTP")
})