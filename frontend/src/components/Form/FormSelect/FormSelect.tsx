import { Controller, useFormContext, type FieldValues } from "react-hook-form"
import type { FormSelectProps } from "./FormSelect.types"
import Select from "../../Select/Select";
import FormFieldWrapper from "../FormFieldWrapper/FormFieldWrapper";


const FormSelect = <T extends FieldValues>({ defaultOption, label, name, options }: FormSelectProps<T>) => {
    const { control } = useFormContext<T>();
    return (
        <Controller
            name={name}
            control={control}
            render={({ field, fieldState: { error } }) => 
                <FormFieldWrapper label={label} error={error?.message} htmlFor={name}>
                    <Select id={name} {...field} options={options} defaultOption={defaultOption} />
                </FormFieldWrapper>
            }
        />
    )
}

export default FormSelect