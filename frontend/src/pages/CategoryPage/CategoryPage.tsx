import { useState } from "react";
import Button from "../../components/Button/Button";
import Loader from "../../components/Loader/Loader";
import { useGetCategoriesQuery } from "../../redux/slices/categoryApiSlice"
import type { CategoryPageModalState } from "./CategoryPage.types";
import AddCategory from "./components/AddCategory/AddCategory";

const CategoryPage = () => {

    const [modal, setModal] = useState<CategoryPageModalState>(null);

    const closeModal = () => setModal(null);

    const { data: categories, isLoading, isFetching } = useGetCategoriesQuery();

    if (isLoading || isFetching) return <Loader />;
    return (
        <div>Categories
            {modal?.type === "ADD_CATEGORY" && <AddCategory onClose={closeModal} />}
            <Button onClick={() => setModal({ type: "ADD_CATEGORY" })}>+ Add New Category</Button>
            <ul>
                {
                    categories?.data.map(category => <li key={category}>{category}</li>)
                }
            </ul>
        </div>
    )
}

export default CategoryPage