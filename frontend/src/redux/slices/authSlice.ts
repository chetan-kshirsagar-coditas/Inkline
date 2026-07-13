import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { AuthState } from "../types";
import type { User } from "../../types/types";

const initialState: AuthState = {
    user: null
}

const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        login: (state: AuthState, action: PayloadAction<{ user: User }>) => {
            state.user = action.payload.user
        },
        logout: (state: AuthState) => {
            state.user = null
        }
    }
})

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;