import type { CategoryData } from "../../pages/CategoryPage/components/AddCategory/AddCategory.types";
import type { CreateCategoryResponse, GetCategoriesResponse } from "../types";
import { apiSliceJAVA } from "./apiSlice";

const categoryApiSlice = apiSliceJAVA.injectEndpoints({
    endpoints: (builder) => ({
        createCategory: builder.mutation<CreateCategoryResponse, CategoryData>({
            query: (data) => ({
                url: `/api/v1/admin/create/category/${data.categoryName}`,
                method: "POST"
            })
        }),
        getCategories: builder.query<GetCategoriesResponse, void>({
            query: () => ({
                url: "/api/v1/common/categories"
            })
        })
    })
})

export const {
    useCreateCategoryMutation,
    useGetCategoriesQuery
} = categoryApiSlice;