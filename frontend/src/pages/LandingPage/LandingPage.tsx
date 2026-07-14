import Navbar from "./components/Navbar/Navbar";
import styles from "./LandingPage.module.scss";
import HeroImage from "../../assets/heroImg.svg";
import LoginForm from "./components/LoginForm/LoginForm";
import { useEffect } from "react";
import { useAppSelector } from "../../redux/store/hooks";
import { useNavigate } from "react-router-dom";

const LandingPage = () => {

    const user = useAppSelector(state => state.auth.user);
    const navigate = useNavigate();
    useEffect(() => {
        if(user) navigate("/dashboardRedirector");
    }, []);
    

    return (
        <div className={styles.LandingPage}>
            <Navbar />
            <div className={styles.heroPage}>

                <div className={styles.leftSection}>
                    <img src={HeroImage} alt="heroImg" />
                </div>

                <div className={styles.rightSection}>
                    <LoginForm />
                </div>
            </div>
        </div>
    )
}

export default LandingPage