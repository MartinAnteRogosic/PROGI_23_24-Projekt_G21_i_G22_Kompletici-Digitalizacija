import React, { useContext } from 'react';
import './HomePage.css';
import { userContext } from '../../userContext';
import { Link } from 'react-router-dom';
import Header from '../Header/Header';
import UploadFiles from '../UploadFiles';
import Footer from '../Footer';


const HomePage = () => {
  const userinfo = JSON.parse(sessionStorage.getItem("user"));
  //const { user } = useContext(userContext);
  const user = {
    "firstName": userinfo.firstName,
    "lastName": userinfo.lastName,
    "role": userinfo.role
  };
  return (
    <div className="home-page">
      <Header />
      <UploadFiles />
      <Footer />
    </div>
  );
};

export default HomePage;
