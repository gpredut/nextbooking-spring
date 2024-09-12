import PropertyTypeList from "../../components/propertyTypeList/PropertyTypeList";
import FeaturedProperties from "../../components/featuredProperties/FeaturedProperties";
import Footer from "../../components/footer/Footer";
import Header from "../../components/header/Header";
import Newsletter from "../../components/newsletter/Newsletter";
import Navbar from "../../components/navbar/Navbar";
import CityList from "../../components/cityList/CityList";
import "./home.css";

const Home = () => {
  return (
    <div>
      <Navbar />
      <Header />
      <div className="homeContainer">
        <h1 className="homeTitle">Explore Romania</h1>
        <CityList />
        <h1 className="homeTitle">Browse by property type</h1>
        <PropertyTypeList />
        <h1 className="homeTitle">Homes guests love</h1>
        <FeaturedProperties />
        <Newsletter />
        <Footer />
      </div>
    </div>
  );
};

export default Home;
