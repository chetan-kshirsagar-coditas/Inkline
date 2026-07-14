import { createBrowserRouter } from "react-router-dom";
import LandingPage from "../pages/LandingPage/LandingPage";
import UnauthorizedPage from "../pages/UnauthorizedPage/UnauthorizedPage";
import AuthGuard from "../hoc/AuthGuard";
import DashboardRedirector from "../hoc/dashboardRedirector";
import DashboardLayout from "../layouts/DashboardLayout/DashboardLayout";

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
                element: <DashboardRedirector/>
            },
            {
                element: <DashboardLayout/>,
            }
        ]
    }
])