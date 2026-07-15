import Loader from "../../components/Loader/Loader";
import { useGetCategoriesQuery } from "../../redux/slices/categoryApiSlice"

const CategoryPage = () => {

    const { data: categories, isLoading, isFetching } = useGetCategoriesQuery();

    if(isLoading || isFetching) return <Loader/>;
    return (
        <div>Categories
            <ul>
                {
                    categories?.data.map(category => <li key={category}>{category}</li>)
                }
            </ul>
        </div>
    )
}

export default CategoryPage