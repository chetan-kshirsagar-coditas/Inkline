import type { Option } from "../../../components/Select/Select.types";
import { ROLE } from "../../../types/types";

export const RoleOptions: Array<Option> = [
    {
        label: ROLE.ADMIN,
        value: ROLE.ADMIN,
    },
    {
        label: ROLE.AUTHOR,
        value: ROLE.AUTHOR,
    },
    {
        label: ROLE.EDITOR,
        value: ROLE.EDITOR,
    },
]