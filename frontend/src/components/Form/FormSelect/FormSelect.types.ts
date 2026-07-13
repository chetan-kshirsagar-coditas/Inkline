import type { FieldValues } from "react-hook-form";
import type { BaseFieldProps } from "../Form.types";
import type { Option } from "../../Select/Select.types";

export interface FormSelectProps<T extends FieldValues> extends BaseFieldProps<T>{
    options: Option[];
    defaultOption: string
}