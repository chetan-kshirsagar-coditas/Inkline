import type { ButtonProps } from "./Button.types"
import styles from "./Button.module.scss";
import { MULTICLASS } from "../../utils/MultiClass";

const Button = ({children, className, variant = "primary", ...props}: ButtonProps) => {
  return <button className={MULTICLASS(styles.btn, styles[variant], className ?? "")}  {...props}>{children}</button>
}

export default Button