import { NavLink, Outlet } from "react-router-dom";
import styles from "./DashboardLayout.module.scss";
import Button from "../../components/Button/Button";

const DashboardLayout = () => {
    return (
        <div className={styles.DashboardLayout}>
            <div className={styles.leftPanel}>

                <span className={styles.name}>InkLine</span>
                <div className={styles.panelLinks}>
                    <NavLink className={styles.panelLink} to={""}>LoremIpsum</NavLink>
                    <NavLink className={styles.panelLink} to={""}>LoremIpsum</NavLink>
                    <NavLink className={styles.panelLink} to={""}>LoremIpsum</NavLink>
                    <NavLink className={styles.panelLink} to={""}>LoremIpsum</NavLink>
                    <NavLink className={styles.panelLink} to={""}>LoremIpsum</NavLink>
                </div>

            </div>
            <div className={styles.rightPanel}>
                <div className={styles.rightPanelNav}>
                    <span>Chetan Kshirsagar</span>
                    <Button variant="danger">Logout</Button>
                </div>
                <div className={styles.outletContainer}>
                    <Outlet />
                </div>
            </div>
        </div>
    )
}

export default DashboardLayout