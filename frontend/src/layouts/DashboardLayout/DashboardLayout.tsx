import { NavLink, Outlet } from "react-router-dom";
import styles from "./DashboardLayout.module.scss";
import Button from "../../components/Button/Button";
import { useAppDispatch, useAppSelector } from "../../redux/store/hooks";
import { NAV_ITEMS_BY_ROLE } from "./constants/NavItems";
import { logout } from "../../redux/slices/authSlice";

const DashboardLayout = () => {

    const dispatch = useAppDispatch();
    const user = useAppSelector(state => state.auth.user);
    const navItems = NAV_ITEMS_BY_ROLE[user?.role! as keyof typeof NAV_ITEMS_BY_ROLE] ?? []

    return (
        <div className={styles.DashboardLayout}>
            <div className={styles.leftPanel}>

                <span className={styles.name}>InkLine</span>
                <div className={styles.panelLinks}>
                    {
                        navItems.map(nav => <NavLink className={styles.panelLink} key={nav.label} to={nav.to}>{nav.label}</NavLink>)
                    }
                </div>

            </div>
            <div className={styles.rightPanel}>
                <div className={styles.rightPanelNav}>
                    <span>{user?.first_name} {user?.last_name}</span>
                    <Button variant="danger" onClick={() => dispatch(logout())}>Logout</Button>
                </div>
                <div className={styles.outletContainer}>
                    <Outlet />
                </div>
            </div>
        </div>
    )
}

export default DashboardLayout