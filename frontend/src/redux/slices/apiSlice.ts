import { fetchBaseQuery } from "@reduxjs/toolkit/query";
import { createApi } from "@reduxjs/toolkit/query/react";

export const apiSlice = createApi({
    reducerPath: "python_be",
    baseQuery: fetchBaseQuery({
        baseUrl: import.meta.env.VITE_BACKEND_BASE_URL,
        prepareHeaders: (headers) => {
            const token = localStorage.getItem("access_token");
            if (token) headers.set("Authorization", `Bearer ${token}`);
            return headers;
        },
        timeout: 30000
    }),
    endpoints: () => ({})
})

export const apiSliceJAVA = createApi({
    reducerPath: "java_be",
    baseQuery: fetchBaseQuery({
        baseUrl: import.meta.env.VITE_BACKEND_BASE_URL_JAVA,
        prepareHeaders: (headers) => {
            const token = localStorage.getItem("access_token");
            if (token) headers.set("Authorization", `Bearer ${token}`);
            headers.set('ngrok-skip-browser-warning', 'true')
            return headers;
        },
        timeout: 30000
    }),
    endpoints: () => ({})
})