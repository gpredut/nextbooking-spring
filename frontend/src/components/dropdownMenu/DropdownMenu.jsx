import React, { useContext, useState, useEffect } from "react";
import { AuthContext } from "../../context/AuthContext"; // Correct import of AuthContext
import { useNavigate } from "react-router-dom";
import "./dropdownMenu.css";

const DropdownMenu = () => {
  const [isOpen, setIsOpen] = useState(false);
  const { user, dispatch } = useContext(AuthContext); // Use AuthContext
  const navigate = useNavigate();

  // Toggle dropdown menu visibility
  const toggleDropdown = () => setIsOpen(!isOpen);

  // Handle user logout
  const handleLogout = () => {
    dispatch({ type: "LOGOUT" });
    navigate("/");
  };

  // Close dropdown if clicking outside of it
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (isOpen && !event.target.closest(".dropdown")) {
        setIsOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [isOpen]);

  return (
    <div className="dropdown">
      <button onClick={toggleDropdown} className="dropdown-toggle">
        {user?.username} ▼
      </button>
      {isOpen && (
        <div className="dropdown-menu">
          <button
            className="dropdown-button"
            onClick={() => navigate("/manage-account")}
          >
            Manage Account
          </button>
          <button
            className="dropdown-button"
            onClick={() => navigate("/bookings-trips")}
          >
            Booking & Trips
          </button>
          <button
            className="dropdown-button"
            onClick={() => navigate("/add-property")}
          >
            List your property
          </button>
          <button className="dropdown-button" onClick={handleLogout}>
            Sign Out
          </button>
        </div>
      )}
    </div>
  );
};

export default DropdownMenu;
