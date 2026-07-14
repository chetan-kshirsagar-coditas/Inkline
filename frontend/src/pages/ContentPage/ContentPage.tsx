import { useState } from "react";
import Button from "../../components/Button/Button";
import Loader from "../../components/Loader/Loader";
import RoleGuard from "../../hoc/RoleGuard";
import { useGetContentQuery } from "../../redux/slices/contentApiSlice"
import { ROLE } from "../../types/types";
import type { ContentPageModalState } from "./ContentPage.types";

const ContentPage = () => {

    const [modal, setModal] = useState<ContentPageModalState>(null);

    const closeModal = () => setModal(null);

    const { data: content, isLoading, isFetching } = useGetContentQuery();
    if (isLoading || isFetching) return <Loader />
    return (
        <div>
            <RoleGuard allowed={[ROLE.AUTHOR]}>
                <Button>+ Add Content</Button>
            </RoleGuard>
            ContentPage
            {JSON.stringify(content ?? "No data.")}

        </div>
    )
}

export default ContentPage