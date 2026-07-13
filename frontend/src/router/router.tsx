import { createBrowserRouter } from "react-router-dom";
import LandingPage from "../pages/LandingPage/LandingPage";
import DashboardLayout from "../layouts/DashboardLayout/DashboardLayout";
import UnauthorizedPage from "../pages/UnauthorizedPage/UnauthorizedPage";
import AuthGuard from "../hoc/AuthGuard";

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
                path: "/dashboard",
                element: <DashboardLayout />
            }
        ]
    }
])