import type { GetContentResponse } from "../types";
import { apiSliceJAVA } from "./apiSlice";

export const contentApiSlice = apiSliceJAVA.injectEndpoints({
    endpoints: (builder) => ({
        getContent: builder.query<GetContentResponse, void>({
            query: () => ({
                url: "/api/v1/author/my-content"
            })
        })
    })
})

export const {
    useGetContentQuery
} = contentApiSlice;