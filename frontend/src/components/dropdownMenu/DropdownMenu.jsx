import { useContext, useState, useEffect } from "react";
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import "./dropdownMenu.css";

const DropdownMenu = () => {
  const [isOpen, setIsOpen] = useState(false);
  const { user, dispatch } = useContext(AuthContext);
  const navigate = useNavigate();

  const toggleDropdown = () => setIsOpen(!isOpen);

  const handleLogout = () => {
    dispatch({ type: "LOGOUT" });
    navigate("/"); // Redirect to home page after logout
  };

  // Hide the menu when clicking outside of it
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
          <button onClick={() => navigate("/manage-account")}>
            Manage Account
          </button>
          <button onClick={() => navigate("/bookings-trips")}>
            Booking & Trips
          </button>
          <button onClick={() => navigate("/list-property")}>
            List your property
          </button>
          <button onClick={handleLogout}>Sign Out</button>
        </div>
      )}
    </div>
  );
};

export default DropdownMenu;
