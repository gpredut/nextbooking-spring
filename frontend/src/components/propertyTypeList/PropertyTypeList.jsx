import useFetch from "../hooks/useFetch.js";
import "./propertyTypeList.css";

const PropertyTypeList = () => {
  const { data, loading } = useFetch(
    "http://localhost:8800/api/properties/countByType"
  );

  const images = [
    "https://i.im.ge/2024/02/14/cWpYDS.photo-1618773928121-c32242e63f39q80w2070autoformatfitcropixlibrb-4-0.jpg",
    "https://i.im.ge/2024/02/14/cWpxey.photo-1560448204-e02f11c3d0e2q80w2070autoformatfitcropixlibrb-4-0.jpg",
    "https://i.im.ge/2024/02/14/cWpAlx.photo-1592555059503-0a774cb8d477q80w1935autoformatfitcropixlibrb-4-0.jpg",
    "https://i.im.ge/2024/02/14/cWpP6L.photo-1564013799919-ab600027ffc6q80w2070autoformatfitcropixlibrb-4-0.jpg",
    "https://i.im.ge/2024/02/14/cWp8SF.photo-1510798831971-661eb04b3739q80w1974autoformatfitcropixlibrb-4-0.jpg",
  ];

  const types = ["Hotel", "Apartment", "Guest House", "Villa", "Cabin"];

  return (
    <div className="pList">
      {loading ? (
        "loading"
      ) : (
        <>
          {images.map((img, i) => {
            const normalizedType = types[i].toLowerCase();
            const typeData = data?.find((d) => d.type === normalizedType);

            return (
              <div className="pListItem" key={i}>
                <img src={img} alt={types[i]} className="pListImg" />
                <div className="pListTitles">
                  <h1>
                    {typeData ? typeData.count : 0} {types[i]}
                  </h1>
                </div>
              </div>
            );
          })}
        </>
      )}
    </div>
  );
};

export default PropertyTypeList;
