import type { PropsWithChildren } from "react";

export interface FormFieldWrapperProps extends PropsWithChildren{
    label: string,
    error? : string,
    htmlFor?: string
}