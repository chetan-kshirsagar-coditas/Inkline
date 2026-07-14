import { type PropsWithChildren } from "react";
import type { ROLE } from "../types/types";
import { useAppSelector } from "../redux/store/hooks";
import { Navigate } from "react-router-dom";

interface RoleGuardProps extends PropsWithChildren {
    allowed: ROLE[],
    isRouterGuard?: boolean
}

const RoleGuard = ({allowed, isRouterGuard = false, children}: RoleGuardProps) => {
    
  const user = useAppSelector(state => state.auth.user);
  console.log(user);
  const isAllowed = allowed.includes(user?.role!);

  return isAllowed ? children : isRouterGuard ? <Navigate to={"/unauthorized"}/> : null;
}

export default RoleGuard