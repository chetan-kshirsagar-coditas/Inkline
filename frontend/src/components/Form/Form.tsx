import { FormProvider } from "react-hook-form"
import type { FormProps } from "./Form.types"
import styles from "./Form.module.scss";
import { MULTICLASS } from "../../utils/MultiClass";

const Form = ({ methods, onSubmit, children, className }: FormProps) => {
    return (
        <FormProvider {...methods}>
            <form onSubmit={methods.handleSubmit(onSubmit)} className={MULTICLASS(styles.form, className ?? "")}>
                {children}
            </form>
        </FormProvider>
    )
}

export default Form