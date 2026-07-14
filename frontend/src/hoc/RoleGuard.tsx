import type { PropsWithChildren } from "react";
import { Navigate } from "react-router-dom";
import useCanAccess from "../hooks/useCanAccess";
import type { ROLE } from "../types/types";

interface RoleGuardProps extends PropsWithChildren {
  allowed: ROLE[];
  isRouterGuard?: boolean;
}

export const RoleGuard = ({ allowed, children, isRouterGuard = false }: RoleGuardProps) => {
  
  const { isAllowed } = useCanAccess(allowed);

  return isAllowed ? children : isRouterGuard ? <Navigate to={"/unauthorized"} />: null;
};

export default RoleGuard;
