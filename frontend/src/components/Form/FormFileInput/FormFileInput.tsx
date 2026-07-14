import { Controller, useFormContext, type FieldValues } from "react-hook-form"
import FormFieldWrapper from "../FormFieldWrapper/FormFieldWrapper";
import Input from "../../Input/Input";
import type { FormFileInputProps } from "./FormFileInput.types";

const FormFileInput = <T extends FieldValues>({ label, name, accept }: FormFileInputProps<T>) => {
    const { control } = useFormContext<T>();
  return (
    <Controller
    name={name}
    control={control}
    render={({ field: { onChange }, fieldState: { error } }) => 
        <FormFieldWrapper label={label} error={error?.message} htmlFor={name}>
            <Input type="file" onChange={(e) => onChange(e.target.files?.[0])} id={name} />
        </FormFieldWrapper>
    }
    />
  )
}

export default FormFileInput