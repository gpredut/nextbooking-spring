import React, { useState } from "react";
import "./listProperty.css";

const ListProperty = () => {
  const [formData, setFormData] = useState({
    name: "",
    type: "",
    city: "",
    address: "",
    distance: "",
    photos: [""],
    desc: "",
    cheapestPrice: "",
    featured: false,
    rooms: [""],
  });

  const handleChange = (e) => {
    const { name, value, type, checked, dataset } = e.target;

    if (name === "photos" || name === "rooms") {
      const index = dataset.index;
      setFormData((prevState) => {
        const updatedArray = [...prevState[name]];
        updatedArray[index] = value;
        return { ...prevState, [name]: updatedArray };
      });
    } else {
      setFormData({
        ...formData,
        [name]: type === "checkbox" ? checked : value,
      });
    }
  };

  const handleArrayChange = (name, index, value) => {
    setFormData((prevState) => {
      const updatedArray = [...prevState[name]];
      updatedArray[index] = value;
      return { ...prevState, [name]: updatedArray };
    });
  };

  const addArrayItem = (name) => {
    setFormData((prevState) => ({
      ...prevState,
      [name]: [...prevState[name], ""],
    }));
  };

  const removeArrayItem = (name, index) => {
    setFormData((prevState) => {
      const updatedArray = prevState[name].filter((_, i) => i !== index);
      return { ...prevState, [name]: updatedArray };
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add your form submission logic here
    console.log("Submitted data:", formData);
  };

  return (
    <div className="list-property-form">
      <h2>Add New Property</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="type">Type:</label>
          <input
            type="text"
            id="type"
            name="type"
            value={formData.type}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="city">City:</label>
          <input
            type="text"
            id="city"
            name="city"
            value={formData.city}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="address">Address:</label>
          <input
            type="text"
            id="address"
            name="address"
            value={formData.address}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="distance">Distance:</label>
          <input
            type="text"
            id="distance"
            name="distance"
            value={formData.distance}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Photos (URLs):</label>
          {formData.photos.map((photo, index) => (
            <div key={index} className="form-group">
              <input
                type="text"
                name="photos"
                data-index={index}
                value={photo}
                onChange={handleChange}
                placeholder={`Photo URL ${index + 1}`}
              />
              <button
                type="button"
                onClick={() => removeArrayItem("photos", index)}
              >
                Remove
              </button>
            </div>
          ))}
          <button type="button" onClick={() => addArrayItem("photos")}>
            Add Photo
          </button>
        </div>
        <div className="form-group">
          <label>Description:</label>
          <textarea
            id="desc"
            name="desc"
            value={formData.desc}
            onChange={handleChange}
            rows="4"
          />
        </div>
        <div className="form-group">
          <label htmlFor="cheapestPrice">Cheapest Price:</label>
          <input
            type="number"
            id="cheapestPrice"
            name="cheapestPrice"
            value={formData.cheapestPrice}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="featured">Featured:</label>
          <input
            type="checkbox"
            id="featured"
            name="featured"
            checked={formData.featured}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label>Rooms:</label>
          {formData.rooms.map((room, index) => (
            <div key={index} className="form-group">
              <input
                type="text"
                name="rooms"
                data-index={index}
                value={room}
                onChange={(e) =>
                  handleArrayChange("rooms", index, e.target.value)
                }
                placeholder={`Room ${index + 1}`}
              />
              <button
                type="button"
                onClick={() => removeArrayItem("rooms", index)}
              >
                Remove
              </button>
            </div>
          ))}
          <button type="button" onClick={() => addArrayItem("rooms")}>
            Add Room
          </button>
        </div>
        <button type="submit">Add Property</button>
      </form>
    </div>
  );
};

export default ListProperty;
