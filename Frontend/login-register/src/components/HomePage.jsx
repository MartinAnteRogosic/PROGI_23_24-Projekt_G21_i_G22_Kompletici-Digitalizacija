import React from 'react';
import './HomePage.css';
import img1 from '../img/ante.png';
import img2 from '../img/ante_zoom.png';
import img3 from '../img/ante_obrnut.png';
import img4 from '../img/ante_inverzni.png';


const HomePage = () => {
  return (
    <div className="home-page">
      <div className="header">
        <div className="user-info">
          <img src={img1} alt="User" className="user-image" />
          <span className="username">User</span>
        </div>

        <div className="center-links">
          <a href="/statistic">Statistic</a>
          <a href="/requests">Requests</a>
        </div>

      </div>
      
      <div className="menu-bar">
        <ul>
          <li>Home</li>
          <li>Archive</li>
          <li>Send</li>
          <li>LogOut</li>
        </ul>
      </div>
    </div>
  );
};

export default HomePage;
