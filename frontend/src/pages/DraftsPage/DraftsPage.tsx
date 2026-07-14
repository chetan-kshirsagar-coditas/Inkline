import { useGetDraftsQuery } from "../../redux/slices/contentApiSlice"

const DraftsPage = () => {
    const { data: drafts } = useGetDraftsQuery();
  return (
    <div>DraftsPage</div>
  )
}

export default DraftsPage