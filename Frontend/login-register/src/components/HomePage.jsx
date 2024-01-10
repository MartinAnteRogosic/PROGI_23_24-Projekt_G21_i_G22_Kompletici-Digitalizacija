import React, { useContext } from 'react';
import './HomePage.css';
import img1 from '../img/ante.png';
import img2 from '../img/ante_zoom.png';
import img3 from '../img/ante_obrnut.png';
import img4 from '../img/ante_inverzni.png';
import { userContext } from '../userContext';
import { Link } from 'react-router-dom';
import Header from './Header';
import UploadFiles from './UploadFiles';


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
      <div className="menu-bar">
        <ul>
          <li>Home</li>
          <li>
            <Link to={`/archive`}>Archive</Link>
          </li>
          <li>Send</li>
          <li>LogOut</li>
        </ul>
      </div>
    </div>
  );
};

export default HomePage;
