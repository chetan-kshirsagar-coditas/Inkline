import { apiSliceJAVA } from "./apiSlice";

const categoryApiSlice = apiSliceJAVA.injectEndpoints({
    endpoints: (builder) => ({
        createCategory: builder.mutation<void, string>({
            query: (categoryName) => ({
                url: `/api/v1/admin/create/category/${categoryName}`,
                method: "POST"
            })
        })
    })
})

const {
    useCreateCategoryMutation
} = categoryApiSlice;