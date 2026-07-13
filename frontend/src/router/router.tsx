import { createBrowserRouter } from "react-router-dom";
import LandingPage from "../pages/LandingPage/LandingPage";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <LandingPage/>
    }
])