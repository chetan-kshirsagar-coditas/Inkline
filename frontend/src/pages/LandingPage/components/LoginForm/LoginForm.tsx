import { useForm } from "react-hook-form"
import type { LoginFormData } from "./LoginForm.types"
import styles from "./LoginForm.module.scss";
import Form from "../../../../components/Form/Form";
import FormInput from "../../../../components/Form/FormInput/FormInput";
import Button from "../../../../components/Button/Button";
import { useState } from "react";
import { snack } from "../../../../components/Snackbar/hooks/useSnackbarStore";

const LoginForm = () => {
    const [isOPTSent, setIsOTPSent] = useState<boolean>(false);
    const methods = useForm<LoginFormData>({ defaultValues: { email: "" } })
    const onSubmit = (data: LoginFormData) => {
        setIsOTPSent(prev => !prev);
        if (isOPTSent) {
            alert(JSON.stringify(data));
            snack.success("OTP verified successfully !!")
        }
        alert(JSON.stringify(data));
        snack.success("OTP sent successfully !!")
    }
    return (
        <div className={styles.LoginForm}>

            <Form methods={methods} onSubmit={onSubmit}>

                {
                    isOPTSent ? (
                        <FormInput<LoginFormData>
                            label="OTP"
                            name="otp"
                            placeholder="Enter OTP here"
                            type="number"
                        />
                    ) : (
                        <FormInput<LoginFormData>
                            label="Email"
                            name="email"
                            placeholder="Enter your email"
                        />
                    )
                }

                <div className={styles.formButtonGrp}>
                    <Button>{isOPTSent ? "Verify OTP" : "Send OTP"}</Button>
                </div>

            </Form>

        </div>
    )
}

export default LoginForm