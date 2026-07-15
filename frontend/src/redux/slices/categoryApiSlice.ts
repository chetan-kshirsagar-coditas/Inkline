import type { CategoryData } from "../../pages/AddCategory/AddCategory.types";
import type { CreateCategoryResponse } from "../types";
import { apiSliceJAVA } from "./apiSlice";

const categoryApiSlice = apiSliceJAVA.injectEndpoints({
    endpoints: (builder) => ({
        createCategory: builder.mutation<CreateCategoryResponse, CategoryData>({
            query: (data) => ({
                url: `/api/v1/admin/create/category/${data.categoryName}`,
                method: "POST"
            })
        })
    })
})

export const {
    useCreateCategoryMutation
} = categoryApiSlice;