import { useForm } from "react-hook-form";
import styles from "./AddUser.module.scss";
import type { RegisterData } from "./AddUser.types";
import { zodResolver } from "@hookform/resolvers/zod";
import { ZRegisterData } from "./AddUser.schema";
import Form from "../../components/Form/Form";
import FormInput from "../../components/Form/FormInput/FormInput";
import FormSelect from "../../components/Form/FormSelect/FormSelect";
import { ROLE } from "../../types/types";
import Button from "../../components/Button/Button";

const AddUser = () => {

    const methods = useForm<RegisterData>({ defaultValues: {
        email: "",
        first_name: "",
        last_name: ""
    }, resolver: zodResolver(ZRegisterData) });

    const onSubmit = (data: RegisterData) => {
        alert(JSON.stringify(data));
    }
  return (
    <div className={styles.addUserPage}>
        <Form methods={methods} onSubmit={onSubmit}>
                <span>Register User</span>

                <FormInput<RegisterData>
                    label="Email"
                    name="email"
                    placeholder="Enter email here"
                />
                <FormInput<RegisterData>
                    label="First Name"
                    name="first_name"
                    placeholder="Enter first name here"
                />
                <FormInput<RegisterData>
                    label="Last Name"
                    name="last_name"
                    placeholder="Enter last name here"
                />
                <FormSelect<RegisterData>
                    label="Role"
                    defaultOption="Select a role"
                    name="role"
                    options={[
                        {
                            label: ROLE.ADMIN,
                            value: ROLE.ADMIN,
                        },
                        {
                            label: ROLE.AUTHOR,
                            value: ROLE.AUTHOR,
                        },
                        {
                            label: ROLE.EDITOR,
                            value: ROLE.EDITOR,
                        },
                    ]}
                    />

                    <div>
                        <Button>Register</Button>
                    </div>
        </Form>
    </div>
  )
}

export default AddUser