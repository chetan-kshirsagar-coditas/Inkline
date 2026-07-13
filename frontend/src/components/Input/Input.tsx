import { MULTICLASS } from "../../utils/MultiClass"
import type { InputProps } from "./Input.types"
import styles from "./Input.module.scss";

const Input = ({ className, ...props }: InputProps) => {
    return <input className={MULTICLASS(styles.input, className ?? "")} {...props}/>
}

export default Input