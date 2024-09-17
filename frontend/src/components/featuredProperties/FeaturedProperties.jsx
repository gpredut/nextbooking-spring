import useFetch from "../hooks/useFetch.js";
import "./featuredProperties.css";

const FeaturedProperties = () => {
  const { data, loading } = useFetch(
    "http://localhost:8800/api/properties/featured"
  );

  return (
    <div className="fp">
      {loading ? (
        "Loading, please wait"
      ) : (
        <>
          {data.map((item) => (
            <div className="fpItem" key={item._id}>
              <img src={item.photos[0]} alt="" className="fpImg" />
              <div className="fpInfo">
                <span className="fpName">{item.name}</span>
                <span className="fpCity">{item.city}</span>
              </div>
              <span className="fpPrice">
                Starting from ${item.cheapestPrice}
              </span>
            </div>
          ))}
        </>
      )}
    </div>
  );
};

export default FeaturedProperties;
