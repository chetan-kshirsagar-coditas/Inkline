import type { RegisterData } from "../../pages/AddUser/AddUser.types";
import type { LoginFormData } from "../../pages/LandingPage/components/LoginForm/LoginForm.types";
import type { User } from "../../types/types";
import type { RequestOTPResponse, VerifyOTPResponse } from "../types";
import { apiSlice } from "./apiSlice";

const authApiSlice = apiSlice.injectEndpoints({
    endpoints: (builder) => ({
        requestOTP: builder.mutation<RequestOTPResponse, LoginFormData>({
            query: (data) => ({
                url: "auth/request_otp",
                method: "POST",
                body: data
            })
        }),
        verifyOTP: builder.mutation<VerifyOTPResponse, LoginFormData>({
            query: (data) => ({
                url: "auth/verify_otp",
                method: "POST",
                body: data
            })
        }),
        getMe: builder.query<User, void>({
            query: () => ({
                url: "auth/me"
            })
        }),
        registerUser: builder.mutation<void, RegisterData>({
            query: ( data ) => ({
                url: "auth/register",
                method: "POST",
                body: data
            })
        })
    })
})

export const {
    useRequestOTPMutation,
    useVerifyOTPMutation,
    useLazyGetMeQuery,
    useRegisterUserMutation
} = authApiSlice;