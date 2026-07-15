import type { Content } from "../pages/ContentPage/ContentPage.types";
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

export type GetContentResponse = {
    data: Content[]
}

export type Draft = {
    "id": string,
    "title": string,
    "body": string,
    "category": string,
    "createdAt": string,
    "contentStatus": string,
    "isSubmitted": boolean
}

export type JavaBEResponseType = {
    message: string,
    success: boolean
}

export type CreateCategoryResponse = JavaBEResponseType;

export type GetDraftsResponse = {
    data: Draft[]
} & JavaBEResponseType

export type GetCategoriesResponse = {
    data: string[]
} & JavaBEResponseType

export type AddContentResponse = JavaBEResponseType;