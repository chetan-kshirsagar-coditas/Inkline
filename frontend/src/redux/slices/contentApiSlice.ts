import type { AddContentData } from "../../pages/ContentPage/components/AddContent/AddContent.types";
import type { AddContentResponse, GetContentResponse, GetDraftsResponse } from "../types";
import { apiSliceJAVA } from "./apiSlice";

export const contentApiSlice = apiSliceJAVA.injectEndpoints({
    endpoints: (builder) => ({
        getContent: builder.query<GetContentResponse, void>({
            query: () => ({
                url: "/api/v1/author/my-content"
            })
        }),
        addContent: builder.mutation<AddContentResponse, any>({
            query: (data) => ({
                url: "/api/v1/author/start-content",
                method: "POST",
                body: data,
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            })
        }),
        getDrafts: builder.query<GetDraftsResponse, void>({
            query: () => ({
                url: "/api/v1/author/my-drafts"
            })
        })
    })
})

export const {
    useGetContentQuery,
    useAddContentMutation,
    useGetDraftsQuery
} = contentApiSlice;