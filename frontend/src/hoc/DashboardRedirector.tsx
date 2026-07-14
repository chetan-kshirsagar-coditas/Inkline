import { Outlet, useNavigate } from "react-router-dom"
import { useAppSelector } from "../redux/store/hooks";
import { useEffect } from "react";
import { ROLE } from "../types/types";

const DashboardRedirector = () => {
    const navigate = useNavigate();
    const user = useAppSelector(state => state.auth.user);

    useEffect(() => {

        if(!user) return;

        switch(user.role){
            case ROLE.ADMIN:
                navigate("/adduser");
                break;
            case ROLE.AUTHOR:
                navigate("/dashboard");
                break;
            case ROLE.EDITOR:
                navigate("/dashboard");
                break;
        }

    }, [user])
  
    return <Outlet/>
}

export default DashboardRedirector