import Loader from "../../components/Loader/Loader";
import { useGetDraftsQuery } from "../../redux/slices/contentApiSlice"

const DraftsPage = () => {

    const { data: drafts, isLoading, isFetching } = useGetDraftsQuery();

    if(isLoading || isFetching) return <Loader/>
  return (
    <div>DraftsPage
        {JSON.stringify(drafts ?? "No data available.")}
    </div>
  )
}

export default DraftsPage