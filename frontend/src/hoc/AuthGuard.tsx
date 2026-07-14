import { Navigate, Outlet, useNavigate } from "react-router-dom"
import { useAppDispatch, useAppSelector } from "../redux/store/hooks";
import { useEffect } from "react";
import { useLazyGetMeQuery } from "../redux/slices/authApiSlice";
import { login } from "../redux/slices/authSlice";
import Loader from "../components/Loader/Loader";

const AuthGuard = () => {

    const dispatch = useAppDispatch();

    const navigate = useNavigate();
    const user = useAppSelector(state => state.auth.user);
    const token = localStorage.getItem("access_token");

    const [getMe, { isLoading, isFetching }] = useLazyGetMeQuery();

    useEffect(() => {

        const fetchUser = async () => {
            if (!token || user) return;
            try {
                const response = await getMe().unwrap();
                dispatch(login({ user: response }))
            } catch (e: any) {
                localStorage.removeItem("access_token");
                localStorage.removeItem("refresh_token");
                navigate("/");
            }
        }
        fetchUser();
    }, [user])

    if (!token) {
        return <Navigate to={"/"} />
    }

    if(isFetching || isLoading) return <Loader/>

    return <Outlet />
}

export default AuthGuard