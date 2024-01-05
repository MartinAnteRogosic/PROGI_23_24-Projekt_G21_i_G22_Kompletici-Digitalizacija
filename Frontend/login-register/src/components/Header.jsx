import React from "react";
import img1 from '../img/ante.png';
import { Link } from "react-router-dom";
import './Header.css';


const Header = () => {
    const userinfo = JSON.parse(sessionStorage.getItem("user"));
    const user = {
        "firstName": userinfo.firstName,
        "lastName": userinfo.lastName,
        "role": userinfo.role
    };

    function handleLogout() {
        sessionStorage.removeItem("user");
        window.location.reload();
    }

    return (
        <div className="header">
          <div className="user-info">
          <img src={img1} alt="User" className="user-image" />
          <span className="username">NAME: { user.firstName + ' ' + user.lastName }</span>
          <span className="username">ROLE: { user.role }</span>
          <button onClick={handleLogout}>Log out</button>
        </div>
        
        <div className="center-links">
          <a href="/statistic">Statistic</a>
          <Link to={`/requests`}>Requests</Link>
        </div>

      </div>
    );
};

export default Header;