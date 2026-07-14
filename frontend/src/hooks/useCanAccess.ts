import { useAppSelector } from "../redux/store/hooks"
import type { ROLE } from "../types/types";

const useCanAccess = (allowed: Array<ROLE>) => {
  const user = useAppSelector(state => state.auth.user);
  console.log("User Details: ", user);
  if (!user) return { isAllowed: false };
  return {
    isAllowed: allowed.includes(user.role)
  };
}

export default useCanAccess