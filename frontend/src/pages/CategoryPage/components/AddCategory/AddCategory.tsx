import { useForm } from "react-hook-form"
import type { AddCategoryProps, CategoryData } from "./AddCategory.types"
import Form from "../../../../components/Form/Form";
import FormInput from "../../../../components/Form/FormInput/FormInput";
import Button from "../../../../components/Button/Button";
import { useCreateCategoryMutation } from "../../../../redux/slices/categoryApiSlice";
import { snack } from "../../../../components/Snackbar/hooks/useSnackbarStore";
import Modal from "../../../../components/Modal/Modal";

const AddCategory = ({ onClose }: AddCategoryProps) => {

    const [createCategory, { isLoading }] = useCreateCategoryMutation();

    const methods = useForm<CategoryData>({ defaultValues: { categoryName: "" } });
    const onSubmit = async (data: CategoryData) => {
        try {
            const response = await createCategory(data).unwrap();
            snack.success(response.message || "Created successfully");
        } catch (e: any) {
            snack.error(e.data.message || "Something went wrong");
        }
    }
    return (

        <Modal closeModal={onClose}>
            <Form methods={methods} onSubmit={onSubmit}>
                <FormInput<CategoryData>
                    label="Category"
                    name="categoryName"
                    placeholder="Enter category here"
                />

                <div>
                    <Button disabled={isLoading}>{isLoading ? "Adding..." : "Add"}</Button>
                </div>
            </Form>
        </Modal>

    )
}

export default AddCategory