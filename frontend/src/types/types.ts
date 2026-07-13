enum ROLE {
    ADMIN = "ADMIN",
    AUTHOR = "AUTHOR",
    EDITOR = "EDITOR"
}

export interface User {
    id: string,
    email: string,
    first_name: string,
    last_name: string,
    profile_picture_url: string,
    role: ROLE
}