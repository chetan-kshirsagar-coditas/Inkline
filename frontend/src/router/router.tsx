import { createBrowserRouter } from "react-router-dom";
import LandingPage from "../pages/LandingPage/LandingPage";
import UnauthorizedPage from "../pages/UnauthorizedPage/UnauthorizedPage";
import AuthGuard from "../hoc/AuthGuard";
import DashboardRedirector from "../hoc/DashboardRedirector";
import DashboardLayout from "../layouts/DashboardLayout/DashboardLayout";
import AddUser from "../pages/AddUser/AddUser";
import RoleGuard from "../hoc/RoleGuard";
import { ROLE } from "../types/types";
import AddCategory from "../pages/AddCategory/AddCategory";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <LandingPage />
    },
    {
        path: "/unauthorized",
        element: <UnauthorizedPage />
    },
    {
        element: <AuthGuard />,
        children: [
            {
                path: "/dashboardRedirector",
                element: <DashboardRedirector />
            },
            {
                element: <DashboardLayout />,
                children: [
                    {
                        path: "adduser",
                        element: <RoleGuard allowed={[ROLE.ADMIN]} isRouterGuard><AddUser /></RoleGuard>
                    },
                    {
                        path: "addcategory",
                        element: <RoleGuard allowed={[ROLE.ADMIN]} isRouterGuard><AddCategory /></RoleGuard>
                    },
                ]
            }
        ]
    }
])