import { useForm } from "react-hook-form"
import type { LoginFormData } from "./LoginForm.types"
import styles from "./LoginForm.module.scss";
import Form from "../../../../components/Form/Form";
import FormInput from "../../../../components/Form/FormInput/FormInput";
import Button from "../../../../components/Button/Button";
import { useState } from "react";
import { snack } from "../../../../components/Snackbar/hooks/useSnackbarStore";
import { zodResolver } from "@hookform/resolvers/zod";
import { ZLoginData } from "./LoginForm.schema";
import { useRequestOTPMutation, useVerifyOTPMutation } from "../../../../redux/slices/authApiSlice";
import { useNavigate } from "react-router-dom";

const LoginForm = () => {
    const [isOPTSent, setIsOTPSent] = useState<boolean>(false);

    const navigate = useNavigate();

    const [requestOTP, { isLoading: requestingOTP }] = useRequestOTPMutation();
    const [verifyOTP, { isLoading: verifyingOTP }] = useVerifyOTPMutation();

    const methods = useForm<LoginFormData>({ defaultValues: { email: "", otp: "" }, resolver: zodResolver(ZLoginData) });

    const onSubmit = async (data: LoginFormData) => {


        try {

            if (!isOPTSent) {
                const response = await requestOTP(data).unwrap();
                snack.success(response.message || "OTP sent successfully");
                setIsOTPSent(true);
                return;
            }

            const response = await verifyOTP(data).unwrap();
            snack.success("OTP verified successfully");
            localStorage.setItem("access_token", response.access_token);

            navigate("/dashboardRedirector");

        } catch (e: any) {
            snack.error(e?.data?.detail || "Something went wrong")
        }
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
                    <Button disabled={ verifyingOTP || requestingOTP }>{isOPTSent ? verifyingOTP ? "Verifying..." : "Verify OTP" : requestingOTP ? "Sending..." : "Send OTP"}</Button>
                </div>

            </Form>

        </div>
    )
}

export default LoginForm