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

const AddContent = ({ onClose }: AddContentProps) => {

    const methods = useForm<AddContentData>({ defaultValues: { 
        title: "",
        body: "",
        category: "",
        file: undefined
     }, resolver: zodResolver(ZAddContent) });

     const onSubmit = (data: AddContentData) => {
        alert(JSON.stringify(data));
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
                    options={[
                        {
                            label: "Technology",
                            value: "Technology"
                        }
                    ]}
                    defaultOption="Select a category"
                />

                <FormFileInput<AddContentData>
                    label="Attachment"
                    name="file"
                    accept="image/png, image/jpg, image/jpeg"
                />

                <div>
                    <Button>Add</Button>
                </div>
            </Form>
        </Modal>
    )
}

export default AddContent