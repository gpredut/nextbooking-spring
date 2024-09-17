import PropertyTypeList from "../../components/propertyTypeList/PropertyTypeList";
import FeaturedProperties from "../../components/featuredProperties/FeaturedProperties";
import CityList from "../../components/cityList/CityList";
import "./home.css";
import Header from "../../components/header/Header";

const Home = () => {
  return (
    <div>
      <Header />
      <div className="homeContainer">
        <h1 className="homeTitle">Explore Romania</h1>
        <CityList />
        <h1 className="homeTitle">Browse by property type</h1>
        <PropertyTypeList />
        <h1 className="homeTitle">Homes guests love</h1>
        <FeaturedProperties />
      </div>
    </div>
  );
};

export default Home;
