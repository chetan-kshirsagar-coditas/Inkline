import { Suspense, type PropsWithChildren } from "react"
import "./App.scss";
import SnackbarContainer from "./components/Snackbar/SnackbarContainer";
import Loader from "./components/Loader/Loader";

const App = ({ children }: PropsWithChildren) => {
  return (
    <div className="App">
      <SnackbarContainer />
      <Suspense fallback={<Loader/>}>
        {children}
      </Suspense>
    </div>
  )
}

export default App