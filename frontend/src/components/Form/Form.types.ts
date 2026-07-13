import type { PropsWithChildren } from "react";
import type { FieldValues, Path, UseFormReturn } from "react-hook-form";

export interface FormProps extends PropsWithChildren{
    onSubmit: (data: any) => void,
    methods: UseFormReturn<any, any, any>,
    className? : string
}

export interface BaseFieldProps<T extends FieldValues>{
    name: Path<T>,
    label: string
}