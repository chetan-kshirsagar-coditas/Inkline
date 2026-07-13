import { Controller, useFormContext, type FieldValues } from "react-hook-form";
import styles from "./FormInput.module.scss";
import type { FormInputProps } from "./FormInput.types";
import Input from "../../Input/Input";
import FormFieldWrapper from "../FormFieldWrapper/FormFieldWrapper";

const FormInput = <T extends FieldValues>({ label, name, disabled, placeholder, type = "text" }: FormInputProps<T>) => {
    const { control } = useFormContext<T>();
    return (
        <Controller
            name={name}
            control={control}
            render={({ field, fieldState: { error } }) =>
                <FormFieldWrapper label={label} error={error?.message} htmlFor={name}>
                    <Input id={name} className={styles.formInput} {...field} disabled={disabled} placeholder={placeholder} type={type} />
                </FormFieldWrapper>
            }
        />
    )
}

export default FormInput