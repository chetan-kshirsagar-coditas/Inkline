import { createBrowserRouter } from "react-router-dom";
import LandingPage from "../pages/LandingPage/LandingPage";
import DashboardLayout from "../layouts/DashboardLayout/DashboardLayout";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <LandingPage />
    },
    {
        path: "/dashboard",
        element: <DashboardLayout />
    }
])