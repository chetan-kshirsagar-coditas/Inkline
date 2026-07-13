import { Navigate, Outlet, useNavigate } from "react-router-dom"
import { useAppSelector } from "../redux/store/hooks";
import { useEffect } from "react";

const AuthGuard = () => {
    const navigate = useNavigate();
    const user = useAppSelector(state => state.auth.user);
    const token = localStorage.getItem("token");

    useEffect(() => {

        const fetchUser = async () => {
            if (!token || user) return;
            try {
                //auth/me api here
            } catch (e: any) {
                localStorage.removeItem("token");
                localStorage.removeItem("refreshToken");
                navigate("/login");
            }
        }
        fetchUser();
    }, [user])

    if(!token) {
        return <Navigate to={"/"}/>
    }
    
    return <Outlet />
}

export default AuthGuard