import { MULTICLASS } from "../../utils/MultiClass";
import styles from "./Select.module.scss";
import type { SelectProps } from "./Select.types";

const Select = ({ options, defaultOption = "Select from the list", className, ...props }: SelectProps) => {
    return <select className={MULTICLASS(styles.select, className ?? "")} {...props}>
        <option value="" disabled>{defaultOption}</option>
        {options?.map(option => <option value={option.value} key={option.value}>{option.label}</option>)}
    </select>
}

export default Select