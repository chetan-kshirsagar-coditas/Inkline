import type { User } from "../types/types";

export interface AuthState {
    user: User | null
}

export type RequestOTPResponse = {
    message: string
}

export type VerifyOTPResponse = {
    access_token: string
}