import React, { useContext } from 'react';
import './HomePage.css';
import img1 from '../img/ante.png';
import img2 from '../img/ante_zoom.png';
import img3 from '../img/ante_obrnut.png';
import img4 from '../img/ante_inverzni.png';
import { userContext } from '../userContext';
import { Link } from 'react-router-dom';


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
      <div className="header">
        { user === null ? (<span>Niste prijavljeni</span>) : (
          <div className="user-info">
          <img src={img1} alt="User" className="user-image" />
          <span className="username">NAME: { user.firstName }</span>
          <br></br>
          <span className="username">ROLE: { user.role }</span>
        </div>
        )}
        

        <div className="center-links">
          <a href="/statistic">Statistic</a>
          <Link to={`/requests`}>Requests</Link>
        </div>

      </div>
      
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
