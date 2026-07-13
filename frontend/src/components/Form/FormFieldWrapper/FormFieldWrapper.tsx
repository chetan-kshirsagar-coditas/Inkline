import type { FormFieldWrapperProps } from "./FormFieldWrapper.types"
import styles from "./FormFIeldWrapper.module.scss";

const FormFieldWrapper = ({ children, label, error, htmlFor }: FormFieldWrapperProps) => {
    return (
        <div className={styles.FormFieldWrapper}>
            <label htmlFor={htmlFor}>{label}</label>
            {children}
            {error && <span className={styles.errMsg}>{error}</span>}
        </div>
    )
}

export default FormFieldWrapper