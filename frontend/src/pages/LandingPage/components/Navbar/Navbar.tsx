import { NavLink } from "react-router-dom";
import styles from "./Navbar.module.scss";

const Navbar = () => {
    return (
        <header className={styles.header}>
            <span className={styles.name}>InkLine</span>
            <nav className={styles.navLinks}>
                <NavLink className={styles.navLink} to={""}>Features</NavLink>
                <NavLink className={styles.navLink} to={""}>About us</NavLink>
                <NavLink className={styles.navLink} to={""}>Contact us</NavLink>
            </nav>
        </header>
    )
}

export default Navbar