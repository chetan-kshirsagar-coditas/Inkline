import { NavLink, Outlet } from "react-router-dom";
import styles from "./DashboardLayout.module.scss";
import Button from "../../components/Button/Button";
import { useAppSelector } from "../../redux/store/hooks";
import { NAV_ITEMS_BY_ROLE } from "./constants/NavItems";

const DashboardLayout = () => {
    // const userRole = useAppSelector(state => state.auth.user?.role);
    // const navItems = NAV_ITEMS_BY_ROLE[userRole! as keyof typeof NAV_ITEMS_BY_ROLE]
    return (
        <div className={styles.DashboardLayout}>
            <div className={styles.leftPanel}>

                <span className={styles.name}>InkLine</span>
                <div className={styles.panelLinks}>
                    <NavLink to={""}>testing</NavLink>
                    {
                        // navItems.map(nav => <NavLink key={nav.label} to={nav.to}>{nav.label}</NavLink>)
                    }
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