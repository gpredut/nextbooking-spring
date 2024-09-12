import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import List from "./pages/list/List";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";
import Property from "./pages/property/Property";
import ListProperty from "./components/listProperty/ListProperty";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/properties" element={<List />} />
        <Route path="/properties/:id" element={<Property />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/list-property" element={<ListProperty />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
