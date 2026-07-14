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
import { useRegisterUserMutation } from "../../redux/slices/authApiSlice";
import { snack } from "../../components/Snackbar/hooks/useSnackbarStore";
import { RoleOptions } from "./constants/RoleOptions";

const AddUser = () => {

    const [registerUser, { isLoading: registeringUser }] = useRegisterUserMutation();

    const methods = useForm<RegisterData>({
        defaultValues: {
            email: "",
            first_name: "",
            last_name: "",
            role: ROLE.AUTHOR
        }, resolver: zodResolver(ZRegisterData)
    });


    const onSubmit = async (data: RegisterData) => {
        try {
            await registerUser(data).unwrap();
            snack.success("Registered successfully.")
        } catch (e: any) {
            snack.error(e.data.detail ||"Something went wrong !");
        }
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
                    options={RoleOptions}
                />

                <div>
                    <Button disabled={registeringUser}>{registeringUser ? "Registering..." : "Register"}</Button>
                </div>
            </Form>
        </div>
    )
}

export default AddUser