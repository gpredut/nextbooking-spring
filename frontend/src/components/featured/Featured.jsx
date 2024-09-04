import useFetch from "../hooks/useFetch.js";
import "./featured.css";

const Featured = () => {
  const { data, loading } = useFetch(
    "http://localhost:8800/api/hotels/countByCity?cities=bucharest,sinaia,hunedoara"
  );

  return (
    <div className="featured">
      {loading ? (
        "Loading, please wait"
      ) : (
        <>
          <div className="featuredItem">
            <img
              src="https://i.im.ge/2024/02/14/cWpWV9.photo-1566827954254-0c0692424c04q80w1974autoformatfitcropixlibrb-4-0.jpg"
              alt=""
              className="featuredImg"
            />
            <div className="featuredTitles">
              <h1>Bucharest</h1>
              <h2>{data[0]} properties</h2>
            </div>
          </div>

          <div className="featuredItem">
            <img
              src="https://i.im.ge/2024/02/14/cWpV08.photo-1608297644370-d98dfbbe30faq80w1964autoformatfitcropixlibrb-4-0.jpg"
              alt=""
              className="featuredImg"
            />
            <div className="featuredTitles">
              <h1>Sinaia</h1>
              <h2>{data[1]} properties</h2>
            </div>
          </div>
          <div className="featuredItem">
            <img
              src="https://i.im.ge/2024/02/14/cWvM51.photo-1599082786861-4f1f038a9fb5q80w1973autoformatfitcropixlibrb-4-0.jpg"
              alt=""
              className="featuredImg"
            />
            <div className="featuredTitles">
              <h1>Hunedoara</h1>
              <h2>{data[2]} properties</h2>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default Featured;
