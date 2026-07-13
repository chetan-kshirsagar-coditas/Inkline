import Navbar from "./components/Navbar/Navbar";
import styles from "./LandingPage.module.scss";
import HeroImage from "../../assets/heroImg.svg";
import LoginForm from "./components/LoginForm/LoginForm";

const LandingPage = () => {

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