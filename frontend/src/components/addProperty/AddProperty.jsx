import React, { useState, useContext } from "react";
import { AuthContext } from "../../context/AuthContext";

const AddProperty = ({ onPropertyAdded }) => {
  const { user, token } = useContext(AuthContext);

  const [name, setName] = useState("");
  const [type, setType] = useState("hotel");
  const [city, setCity] = useState("");
  const [address, setAddress] = useState("");
  const [distance, setDistance] = useState("");
  const [photos, setPhotos] = useState([]);
  const [description, setDescription] = useState("");
  const [cheapestPrice, setCheapestPrice] = useState("");
  const [featured, setFeatured] = useState(false);
  const [rooms, setRooms] = useState([]);
  const [photoInput, setPhotoInput] = useState("");
  const [roomInput, setRoomInput] = useState("");
  const [error, setError] = useState(""); // For error messages

  // Fallback for onPropertyAdded if it's not a function
  const handlePropertyAdded =
    typeof onPropertyAdded === "function" ? onPropertyAdded : () => {};

  const handleAddPhoto = () => {
    if (photoInput.trim()) {
      setPhotos([...photos, photoInput.trim()]);
      setPhotoInput("");
    }
  };

  const handleAddRoom = () => {
    if (roomInput.trim()) {
      setRooms([...rooms, roomInput.trim()]);
      setRoomInput("");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newProperty = {
      name,
      type,
      city,
      address,
      distance: parseFloat(distance),
      photos,
      description,
      cheapestPrice: parseInt(cheapestPrice),
      featured,
      rooms,
    };

    console.log("Submitting property:", newProperty);
    console.log("Token being sent:", token); // Log token

    try {
      const response = await fetch("http://localhost:8800/api/properties/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`, // Ensure token is included
        },
        body: JSON.stringify(newProperty),
      });

      if (response.ok) {
        handlePropertyAdded(); // Call the function to update parent component
        alert("Property added successfully!");
      } else if (response.status === 409) {
        setError("Property with the same address already exists.");
      } else {
        console.error(
          "Failed to add property:",
          response.status,
          response.statusText
        );
        alert("Failed to add property: " + response.statusText);
      }
    } catch (error) {
      console.error("Error:", error);
      setError("An error occurred while adding the property.");
    }
  };

  // Check if the user is an OWNER
  const isOwner = user?.role === "OWNER";

  return (
    <div>
      {isOwner ? (
        <>
          <h2>Add New Property</h2>
          <form onSubmit={handleSubmit}>
            <div>
              <label>Name:</label>
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>
            <div>
              <label>Type:</label>
              <select
                value={type}
                onChange={(e) => setType(e.target.value)}
                required
              >
                <option value="hotel">Hotel</option>
                <option value="apartment">Apartment</option>
                <option value="villa">Villa</option>
                <option value="cabin">Cabin</option>
                <option value="guest house">Guest House</option>
              </select>
            </div>
            <div>
              <label>City:</label>
              <input
                type="text"
                value={city}
                onChange={(e) => setCity(e.target.value)}
                required
              />
            </div>
            <div>
              <label>Address:</label>
              <input
                type="text"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                required
              />
            </div>
            <div>
              <label>Distance (km):</label>
              <input
                type="number"
                value={distance}
                onChange={(e) => setDistance(e.target.value)}
                required
              />
            </div>
            <div>
              <label>Photos (add one at a time):</label>
              <input
                type="text"
                value={photoInput}
                onChange={(e) => setPhotoInput(e.target.value)}
                placeholder="Enter photo URL"
              />
              <button type="button" onClick={handleAddPhoto}>
                Add Photo
              </button>
              <div>
                {photos.map((photo, index) => (
                  <img key={index} src={photo} alt={`Property ${index}`} />
                ))}
              </div>
            </div>
            <div>
              <label>Description:</label>
              <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                required
              />
            </div>
            <div>
              <label>Cheapest Price:</label>
              <input
                type="number"
                value={cheapestPrice}
                onChange={(e) => setCheapestPrice(e.target.value)}
                required
              />
            </div>
            <div>
              <label>Featured:</label>
              <input
                type="checkbox"
                checked={featured}
                onChange={() => setFeatured(!featured)}
              />
            </div>
            <div>
              <label>Rooms (add one at a time):</label>
              <input
                type="text"
                value={roomInput}
                onChange={(e) => setRoomInput(e.target.value)}
                placeholder="Enter room number"
              />
              <button type="button" onClick={handleAddRoom}>
                Add Room
              </button>
              <div>
                {rooms.map((room, index) => (
                  <p key={index}>Room {room}</p>
                ))}
              </div>
            </div>
            <button type="submit">Add Property</button>
            {error && <p style={{ color: "red" }}>{error}</p>}
          </form>
        </>
      ) : (
        <p>You do not have permission to add properties.</p>
      )}
    </div>
  );
};

export default AddProperty;
