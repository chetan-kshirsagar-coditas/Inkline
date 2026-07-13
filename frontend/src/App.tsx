import type { PropsWithChildren } from "react"
import "./App.scss";
import SnackbarContainer from "./components/Snackbar/SnackbarContainer";

const App = ({ children }: PropsWithChildren) => {
  return (
    <div className="App">
      <SnackbarContainer />
      {children}
    </div>
  )
}

export default App