import { useForm } from "react-hook-form"
import Modal from "../../../../components/Modal/Modal"
import type { AddContentData, AddContentProps } from "./AddContent.types"
import { zodResolver } from "@hookform/resolvers/zod";
import { ZAddContent } from "./AddContent.schema";
import Form from "../../../../components/Form/Form";
import FormInput from "../../../../components/Form/FormInput/FormInput";
import FormSelect from "../../../../components/Form/FormSelect/FormSelect";
import Button from "../../../../components/Button/Button";
import FormFileInput from "../../../../components/Form/FormFileInput/FormFileInput";
import { useAddContentMutation } from "../../../../redux/slices/contentApiSlice";
import { snack } from "../../../../components/Snackbar/hooks/useSnackbarStore";
import { useGetCategoriesQuery } from "../../../../redux/slices/categoryApiSlice";
import type { Option } from "../../../../components/Select/Select.types";

const AddContent = ({ onClose }: AddContentProps) => {

    const [addContent, { isLoading }] = useAddContentMutation();

    const { data: categories, isLoading: loadingCategories, isFetching: fetchingCategories } = useGetCategoriesQuery();

    const methods = useForm<AddContentData>({
        defaultValues: {
            title: "",
            body: "",
            category: "",
            file: undefined
        }, resolver: zodResolver(ZAddContent)
    });

    const onSubmit = async (data: AddContentData) => {

        const formData = new FormData();

        formData.append("title", data.title);
        formData.append("body", data.body);
        if (data.category) formData.append("category", data.category);
        if (data.file) formData.append("file", data.file);

        try {
            const response = await addContent(formData).unwrap();
            snack.success(response.message || "Added successfully");
        } catch (e: any) {
            snack.error(e?.data?.message || "Something went wrong !");
        }
    }
    return (
        <Modal closeModal={onClose}>
            <Form methods={methods} onSubmit={onSubmit}>
                <span>Add Content</span>
                <FormInput<AddContentData>
                    label="Title"
                    name="title"
                    placeholder="Title here"
                />
                <FormInput<AddContentData>
                    label="Body"
                    name="body"
                    placeholder="body here"
                />
                <FormSelect<AddContentData>
                    label="Category"
                    name="category"
                    defaultOption={loadingCategories || fetchingCategories ? "Loading categories..." : "Select a category"}
                    options={
                        categories?.data.map(category => {
                            return { label: category, value: category } as Option
                        }) || []
                    }
                />

                <FormFileInput<AddContentData>
                    label="Attachment"
                    name="file"
                    accept="image/png, image/jpg, image/jpeg"
                />

                <div>
                    <Button disabled={isLoading}>{isLoading ? "Adding..." : "Add"}</Button>
                </div>
            </Form>
        </Modal>
    )
}

export default AddContent