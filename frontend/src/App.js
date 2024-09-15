import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import List from "./pages/list/List";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";
import Property from "./pages/property/Property";
import AddProperty from "./components/addProperty/AddProperty";
import { AuthContextProvider } from "./context/AuthContext";
import { SearchContextProvider } from "./context/SearchContext";
import Navbar from "./components/navbar/Navbar";

function App() {
  return (
    <AuthContextProvider>
      <SearchContextProvider>
        <BrowserRouter>
          <Navbar />{" "}
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/properties" element={<List />} />
            <Route path="/properties/:id" element={<Property />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/add-property" element={<AddProperty />} />
          </Routes>
        </BrowserRouter>
      </SearchContextProvider>
    </AuthContextProvider>
  );
}

export default App;
