import { useForm } from "react-hook-form"
import type { LoginFormData } from "./LoginForm.types"
import styles from "./LoginForm.module.scss";
import Form from "../../../../components/Form/Form";
import FormInput from "../../../../components/Form/FormInput/FormInput";
import Button from "../../../../components/Button/Button";

const LoginForm = () => {
    const methods = useForm<LoginFormData>({ defaultValues: { email: "" } })
    const onSubmit = (data: LoginFormData) => {
        alert(JSON.stringify(data));
    }
    return (
        <div className={styles.LoginForm}>

            <Form methods={methods} onSubmit={onSubmit}>

                <FormInput<LoginFormData>
                    label="Email"
                    name="email"
                    placeholder="Enter your email"
                />

                <div className={styles.formButtonGrp}>
                    <Button>Log In</Button>
                </div>

            </Form>

        </div>
    )
}

export default LoginForm